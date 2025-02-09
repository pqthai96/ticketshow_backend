package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.UserDTO;
import com.aptech.ticketshow.data.dtos.UserProfileDTO;
import com.aptech.ticketshow.data.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.aptech.ticketshow.data.entities.User;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();

    UserDTO findById(Long id);

    UserProfileDTO editUser(UserProfileDTO userProfileDTO);

    UserDTO verifyEmailUser(UserDTO userDTO);

    UserDTO changePasswordUser(UserDTO userDTO);

    UserProfileDTO findByEmail(String email);

    UserDTO create(UserDTO userDTO);

    boolean existsByEmail(String email);
}
