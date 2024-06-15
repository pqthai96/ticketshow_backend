package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();

    UserDTO findById(Long id);

    UserDTO editUser(UserDTO userDTO);

    UserDTO verifyEmailUser(UserDTO userDTO);

    UserDTO changePasswordUser(UserDTO userDTO);

    UserDTO findByEmail(String email);

    UserDetailsService userDetailsService();
}
