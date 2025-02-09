package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.request.SignInRequest;
import com.aptech.ticketshow.data.dtos.request.SignUpRequest;
import com.aptech.ticketshow.data.dtos.response.AuthResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> authenticate(SignInRequest signInRequest);

    AuthResponse registerUser(SignUpRequest signUpRequest);
}