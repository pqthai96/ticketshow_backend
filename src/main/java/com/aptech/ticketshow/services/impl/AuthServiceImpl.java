package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.StatusDTO;
import com.aptech.ticketshow.data.dtos.response.JwtAuthResponse;
import com.aptech.ticketshow.data.dtos.request.RefreshTokenRequest;
import com.aptech.ticketshow.data.dtos.request.SigningRequest;
import com.aptech.ticketshow.data.dtos.UserDTO;
import com.aptech.ticketshow.data.entities.ERole;
import com.aptech.ticketshow.data.entities.Status;
import com.aptech.ticketshow.data.entities.User;
import com.aptech.ticketshow.data.mappers.UserMapper;
import com.aptech.ticketshow.data.repositories.UserRepository;
import com.aptech.ticketshow.services.AuthService;
import com.aptech.ticketshow.services.JWTService;
import com.aptech.ticketshow.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    private final UserService userService;

    public User signup(UserDTO userDTO){
        User user = new User();
        user = userMapper.toEntity(userDTO);
        user.setStatus(new Status(1L, "Active"));
        user.setRole(ERole.ROLE_USER);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(user);
    }
    @Override
    public JwtAuthResponse signin(SigningRequest signingRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signingRequest.getEmail(), signingRequest.getPassword()));
        UserDetails userDetails = userService.loadUserByUsername(signingRequest.getEmail());
        var user = userService.findByEmail(signingRequest.getEmail());

        // Thêm thông tin người dùng vào JWT
        Map<String, Object> additionalClaims = new HashMap<>();
        additionalClaims.put("id", user.getId());
        additionalClaims.put("role", user.getRole());
        additionalClaims.put("email", user.getEmail());
        additionalClaims.put("phone", user.getPhone());
        additionalClaims.put("firstName", user.getFirstName());
        additionalClaims.put("lastName", user.getLastName());
        additionalClaims.put("emailVerified", user.getEmailVerified());

        String jwtToken = jwtService.generateToken(userDetails, additionalClaims);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), userDetails);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setToken(jwtToken);
        jwtAuthResponse.setRefreshToken(refreshToken);

        return jwtAuthResponse;
    }

    @Override
    public JwtAuthResponse refreshToken(String refreshToken) {
        // Xử lý logic cập nhật token mới từ refreshToken
        // Bạn có thể tham khảo mã nguồn trong phần AuthServiceImpl của bạn
        // và implement logic xử lý refreshToken ở đây

        return null; // Hoặc trả về một đối tượng JwtAuthResponse mới
    }
}
