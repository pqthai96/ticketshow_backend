package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.common.config.JwtUtil;
import com.aptech.ticketshow.data.dtos.AdminDTO;
import com.aptech.ticketshow.data.dtos.request.AdminLoginRequest;
import com.aptech.ticketshow.data.dtos.response.AdminLoginResponse;
import com.aptech.ticketshow.data.entities.Admin;
import com.aptech.ticketshow.data.mappers.AdminMapper;
import com.aptech.ticketshow.data.repositories.AdminRepository;
import com.aptech.ticketshow.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<AdminDTO> findAll() {
        return adminRepository.findAll().stream().map(r -> adminMapper.toDTO(r)).collect(Collectors.toList());
    }

    @Override
    public AdminDTO findById(Long id) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        if (adminOptional.isPresent()) {
            return adminMapper.toDTO(adminOptional.get());
        } else {
            throw new RuntimeException("Admin not found with id: " + id);
        }
    }

    @Override
    public AdminDTO findByAdminName(String adminName) {
        return adminMapper.toDTO(adminRepository.findByAdminName(adminName));
    }
}
