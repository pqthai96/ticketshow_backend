package com.aptech.ticketshow.controllers;

import com.aptech.ticketshow.common.config.JwtUtil;
import com.aptech.ticketshow.data.dtos.MailDTO;
import com.aptech.ticketshow.data.dtos.UserDTO;
import com.aptech.ticketshow.data.dtos.UserProfileDTO;
import com.aptech.ticketshow.data.dtos.request.SignInRequest;
import com.aptech.ticketshow.data.dtos.request.SignUpRequest;
import com.aptech.ticketshow.services.AuthService;
import com.aptech.ticketshow.services.MailService;
import com.aptech.ticketshow.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

        return ResponseEntity.ok(mailService.sendMailWithToken(mailDTO, userDTO.getVerificationToken()));
    }

    @GetMapping("/verify")
    public RedirectView verify(@RequestParam("token") String token) {
        UserDTO userDTO = userService.findUserByVerificationToken(token);

        userDTO.setVerified(true);
        userService.update(userDTO);

        return new RedirectView("http://localhost:3000/verified");
    }
}