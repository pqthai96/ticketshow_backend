package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.AdminDTO;
import com.aptech.ticketshow.data.dtos.UserDTO;

import java.util.List;

public interface AdminService {

    List<AdminDTO> findAll();
}
