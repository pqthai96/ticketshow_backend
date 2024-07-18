package com.aptech.ticketshow.controllers;

import com.aptech.ticketshow.data.dtos.StatusDTO;
import com.aptech.ticketshow.data.dtos.request.OtpVerificationRequest;
import com.aptech.ticketshow.data.dtos.response.JwtAuthResponse;
import com.aptech.ticketshow.data.dtos.request.RefreshTokenRequest;
import com.aptech.ticketshow.data.dtos.request.SigningRequest;
import com.aptech.ticketshow.data.dtos.UserDTO;
import com.aptech.ticketshow.data.entities.ERole;
import com.aptech.ticketshow.data.entities.Status;
import com.aptech.ticketshow.data.entities.User;
import com.aptech.ticketshow.services.AuthService;
import com.aptech.ticketshow.services.SmsService;
import com.aptech.ticketshow.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final SmsService smsService;

    private final ConcurrentHashMap<String, OtpEntry> otpStorage = new ConcurrentHashMap<>();

    private static class OtpEntry {
        String otp;
        long expirationTime;

        OtpEntry(String otp, long expirationTime) {
            this.otp = otp;
            this.expirationTime = expirationTime;
        }
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody UserDTO userDTO) {
        // Generate a random OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        smsService.sendSms(userDTO.getPhone(), "Your OTP is: " + otp);

        long expirationTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5);
        otpStorage.put(userDTO.getPhone(), new OtpEntry(otp, expirationTime));

        return ResponseEntity.ok("OTP sent successfully");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpVerificationRequest request) {
        OtpEntry otpEntry = otpStorage.get(request.getPhoneNumber());
        if (otpEntry != null && otpEntry.otp.equals(request.getOtp())) {
            if (System.currentTimeMillis() <= otpEntry.expirationTime) {
                // OTP is correct and not expired, remove it from the storage
                otpStorage.remove(request.getPhoneNumber());
                return ResponseEntity.ok("OTP verified successfully");
            } else {
                otpStorage.remove(request.getPhoneNumber());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP expired");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO){
        //OtpEntry otpEntry = otpStorage.get(userDTO.getPhone());
        //if (otpEntry != null && otpEntry.otp.equals(userDTO.getOtp())) {
            //if (System.currentTimeMillis() <= otpEntry.expirationTime) {
                User user = authService.signup(userDTO);
                return ResponseEntity.ok(user);
            //} else {
                // otpStorage.remove(userDTO.getPhone());
                // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP expired");
           // }
        //} else {
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
        //}
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> signin(@RequestBody SigningRequest signingRequest) {
        JwtAuthResponse jwtAuthResponse = authService.signin(signingRequest);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/send-email-verification/{id}")
    public ResponseEntity<UserDTO> verifyEmail(@PathVariable Long id, @RequestBody UserDTO userDTO){
        userDTO.setId(id);
        UserDTO editedUser = authService.verifyEmailUser(userDTO);
        return ResponseEntity.ok(editedUser);
    }

    @GetMapping("/check-email-verification/{id}")
    public ResponseEntity<?> checkEmailVerificationStatus(@PathVariable Long id) {
        UserDTO userDTO = authService.findById(id);
        boolean isVerified = userDTO.getEmailVerified() != null;
        return ResponseEntity.ok(Collections.singletonMap("isVerified", isVerified));
    }
}
