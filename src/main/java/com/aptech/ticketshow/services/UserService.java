package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.UserDTO;
import com.aptech.ticketshow.data.entities.User;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();

    // Bổ sung phương thức getById
    UserDTO getById(Long id);
}
