package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.common.config.JwtUtil;
import com.aptech.ticketshow.data.dtos.MailDTO;
import com.aptech.ticketshow.data.dtos.UserDTO;
import com.aptech.ticketshow.data.dtos.request.SignInRequest;
import com.aptech.ticketshow.data.dtos.request.SignUpRequest;
import com.aptech.ticketshow.data.dtos.response.AuthResponse;
import com.aptech.ticketshow.data.entities.Status;
import com.aptech.ticketshow.data.entities.User;
import com.aptech.ticketshow.data.mappers.UserMapper;
import com.aptech.ticketshow.data.repositories.StatusRepository;
import com.aptech.ticketshow.data.repositories.UserRepository;
import com.aptech.ticketshow.services.AuthService;
import com.aptech.ticketshow.services.MailService;
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

    @Autowired
    private MailService mailService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseEntity<?> authenticate(SignInRequest signInRequest) {
        Optional<User> userOptional = userRepository.findByEmail(signInRequest.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {

                String token = jwtUtil.generateToken(user.getEmail());
                return ResponseEntity.ok(new AuthResponse(user.getEmail(), user.getFirstName(), user.getLastName(), user.getAvatarImagePath(), token, "Sign In Successfully!"));
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
        newUser.setVerified(false);

        Status activeStatus = statusRepository.findById(1L).orElseThrow();
        newUser.setStatus(activeStatus);

        userRepository.save(newUser);

        String token = jwtUtil.generateToken(newUser.getEmail());

        return new AuthResponse(newUser.getEmail(), newUser.getFirstName(), newUser.getLastName(), newUser.getAvatarImagePath(), token, "Sign In Successfully!");
    }

    @Override
    public void processForgotPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();

        String token = jwtUtil.generateRandomToken();
        user.setForgotPasswordToken(token);

        userRepository.save(user);
        MailDTO mailDTO = new MailDTO();
        mailDTO.setName(user.getFirstName());
        mailDTO.setTo(email);
        mailDTO.setSubject("Reset Password From Ovation");
        mailDTO.setBody("We have received a request to reset the password for your account. Please click the button below to continue.");

        mailService.sendMailWithToken(mailDTO, token);
    }

    @Override
    public void verifyPasswordResetToken(String email, String token) {
        userRepository.findByEmailAndForgotPasswordToken(email, token).orElseThrow();
    }

    @Override
    public void resetPassword(String email, String token, String newPassword) {
        User user = userRepository.findByEmailAndForgotPasswordToken(email, token)
                .orElseThrow();

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setForgotPasswordToken(null);

        userRepository.save(user);
    }

    @Override
    public boolean changePassword(UserDTO userDTO, String currentPassword, String newPassword) {

        if (!passwordEncoder.matches(currentPassword, userDTO.getPassword())) {
            return false;
        }

        userDTO.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userMapper.toEntity(userDTO));

        return true;
    }
}