package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

public interface JWTService {
    String generateToken(UserDetails userDetails, Map<String, Object> additionalClaims);

    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);

    String extractUserName(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

}
