package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.response.JwtAuthResponse;
import com.aptech.ticketshow.data.dtos.request.RefreshTokenRequest;
import com.aptech.ticketshow.data.dtos.request.SigningRequest;
import com.aptech.ticketshow.data.dtos.UserDTO;
import com.aptech.ticketshow.data.entities.User;

public interface AuthService {

    User signup(UserDTO userDTO);

    JwtAuthResponse signin(SigningRequest signingRequest);

    JwtAuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
