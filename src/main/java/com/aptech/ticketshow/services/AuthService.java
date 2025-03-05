package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.AdminDTO;
import com.aptech.ticketshow.data.dtos.UserDTO;
import com.aptech.ticketshow.data.dtos.request.SignInRequest;
import com.aptech.ticketshow.data.dtos.request.SignUpRequest;
import com.aptech.ticketshow.data.dtos.response.AuthResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> authenticate(SignInRequest signInRequest);

    AuthResponse registerUser(SignUpRequest signUpRequest);

    void processForgotPassword(String email);

    void verifyPasswordResetToken(String email, String token);

    void resetPassword(String email, String token, String newPassword);

    boolean changePassword(UserDTO userDTO, String currentPassword, String newPassword);
}