package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.response.JwtAuthResponse;
import com.aptech.ticketshow.data.dtos.request.RefreshTokenRequest;
import com.aptech.ticketshow.data.dtos.request.SigningRequest;
import com.aptech.ticketshow.data.dtos.UserDTO;
import com.aptech.ticketshow.data.entities.ERole;
import com.aptech.ticketshow.data.entities.User;
import com.aptech.ticketshow.data.mappers.UserMapper;
import com.aptech.ticketshow.data.repositories.UserRepository;
import com.aptech.ticketshow.services.AuthService;
import com.aptech.ticketshow.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    public User signup(UserDTO userDTO){
        User user = new User();
        user = userMapper.toEntity(userDTO);
        user.setRole(ERole.ROLE_USER);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmailVerified(userDTO.getEmailVerified());
        user.setCreatedAt(userDTO.getCreatedAt());
        user.setUpdatedAt(userDTO.getUpdatedAt());
        return userRepository.save(user);
    }

    public JwtAuthResponse signin(SigningRequest signingRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signingRequest.getEmail(), signingRequest.getPassword()));
        var user = userRepository.findByEmail(signingRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setToken(jwt);
        jwtAuthResponse.setRefreshToken(refreshToken);

        return jwtAuthResponse;
    }

    public JwtAuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            var jwt = jwtService.generateToken(user);

            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
            jwtAuthResponse.setToken(jwt);
            jwtAuthResponse.setRefreshToken(refreshTokenRequest.getToken());

            return jwtAuthResponse;
        }
        return null;
    }
}
