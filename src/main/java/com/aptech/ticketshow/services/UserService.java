package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();
}
