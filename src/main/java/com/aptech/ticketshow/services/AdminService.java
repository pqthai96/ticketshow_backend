package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.AdminDTO;
import com.aptech.ticketshow.data.dtos.request.AdminLoginRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {

    List<AdminDTO> findAll();

    AdminDTO findById(Long id);

    AdminDTO findByAdminName(String adminName);
}
