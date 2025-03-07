package com.aptech.ticketshow.controllers;

import com.aptech.ticketshow.common.config.JwtUtil;
import com.aptech.ticketshow.data.dtos.MailDTO;
import com.aptech.ticketshow.data.dtos.UserDTO;
import com.aptech.ticketshow.data.dtos.UserProfileDTO;
import com.aptech.ticketshow.data.dtos.request.*;
import com.aptech.ticketshow.exception_v2.ResourceNotFoundExceptionV2;
import com.aptech.ticketshow.exception_v2.TokenInvalidExceptionV2;
import com.aptech.ticketshow.services.AuthService;
import com.aptech.ticketshow.services.ImageUploadService;
import com.aptech.ticketshow.services.MailService;
import com.aptech.ticketshow.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private ImageUploadService imageUploadService;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SignInRequest signInRequest) {
        return authService.authenticate(signInRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authService.registerUser(signUpRequest));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token) {
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String email = jwtUtil.extractEmail(token);
        UserProfileDTO userProfileDTO = userService.findByEmail(email);
        return ResponseEntity.ok(userProfileDTO);
    }

    @GetMapping("/send-verify-email")
    public ResponseEntity<?> sendVerifyEmail(@RequestHeader("Authorization") String token) {
        UserDTO userDTO = jwtUtil.extractUser(token);
        userDTO.setVerificationToken(jwtUtil.generateRandomToken());

        UserDTO updatedUser = userService.update(userDTO);

        MailDTO mailDTO = new MailDTO();
        mailDTO.setName(updatedUser.getFirstName() + " " + updatedUser.getLastName());
        mailDTO.setTo(userDTO.getEmail());
        mailDTO.setSubject("Verification Email From Ovation");
        mailDTO.setBody("Thank you for registering and using Ovation's services!");

        return ResponseEntity.ok(mailService.sendVerifyEmailWithToken(mailDTO, userDTO.getVerificationToken()));
    }

    @GetMapping("/verify")
    public RedirectView verify(@RequestParam("token") String token) {
        UserDTO userDTO = userService.findUserByVerificationToken(token);

        userDTO.setVerified(true);
        userService.update(userDTO);

        return new RedirectView(frontendUrl + "/verify-success");
    }

    @PostMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@RequestHeader("Authorization") String token, @RequestBody UserProfileDTO userProfileDTO) {
        return ResponseEntity.ok(userService.updateProfile(jwtUtil.extractUser(token), userProfileDTO));
    }

    @PostMapping("/avatar")
    public ResponseEntity<?> updateAvatar(@RequestHeader("Authorization") String token, @RequestParam("avatar") MultipartFile avatar) {
        UserDTO userDTO = jwtUtil.extractUser(token);
        userDTO.setAvatarImagePath(imageUploadService.uploadAvatarImage(avatar, userDTO.getEmail()));
        return ResponseEntity.ok(userService.update(userDTO));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        try {
            authService.processForgotPassword(forgotPasswordRequest.getEmail());
            return ResponseEntity.ok("Password reset email sent!");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/verify-reset-token")
    public ResponseEntity<?> verifyResetToken(@RequestBody VerifyResetTokenRequest verifyResetTokenRequest) {
        try {
            authService.verifyPasswordResetToken(verifyResetTokenRequest.getEmail(), verifyResetTokenRequest.getToken());
            return ResponseEntity.ok("Token accepted!");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        try {
            authService.resetPassword(resetPasswordRequest.getEmail(), resetPasswordRequest.getToken(), resetPasswordRequest.getPassword());
            return ResponseEntity.ok("Reset password successfully!");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String token, @RequestBody ChangePasswordRequest changePasswordRequest) {
        UserDTO userDTO = jwtUtil.extractUser(token);
        return ResponseEntity.ok(authService.changePassword(userDTO, changePasswordRequest.getCurrentPassword(), changePasswordRequest.getNewPassword()));
    }
}