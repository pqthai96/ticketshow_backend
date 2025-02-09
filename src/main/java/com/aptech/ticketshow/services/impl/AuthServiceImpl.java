package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.common.config.JwtUtil;
import com.aptech.ticketshow.data.dtos.request.SignInRequest;
import com.aptech.ticketshow.data.dtos.request.SignUpRequest;
import com.aptech.ticketshow.data.dtos.response.AuthResponse;
import com.aptech.ticketshow.data.entities.Status;
import com.aptech.ticketshow.data.entities.User;
import com.aptech.ticketshow.data.repositories.StatusRepository;
import com.aptech.ticketshow.data.repositories.UserRepository;
import com.aptech.ticketshow.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public ResponseEntity<?> authenticate(SignInRequest signInRequest) {
        Optional<User> userOptional = userRepository.findByEmail(signInRequest.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {

                String token = jwtUtil.generateToken(user.getEmail());
                return ResponseEntity.ok(new AuthResponse(user.getEmail(), user.getFirstName(), user.getLastName(), token, "Sign In Successfully!"));
            }
        }

        return ResponseEntity.status(401).body("Wrong email/phone or password");
    }

    @Override
    public AuthResponse registerUser(SignUpRequest signUpRequest) {

        User newUser = new User();
        newUser.setEmail(signUpRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        newUser.setFirstName(signUpRequest.getFirstName());
        newUser.setLastName(signUpRequest.getLastName());

        Status activeStatus = statusRepository.findById(1L).orElseThrow();
        newUser.setStatus(activeStatus);

        userRepository.save(newUser);

        String token = jwtUtil.generateToken(newUser.getEmail());

        return new AuthResponse(newUser.getEmail(), newUser.getFirstName(), newUser.getLastName(), token, "Sign In Successfully!");
    }
}