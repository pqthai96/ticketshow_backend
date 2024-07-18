package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.AdminDTO;
import com.aptech.ticketshow.data.entities.Admin;
import com.aptech.ticketshow.data.mappers.AdminMapper;
import com.aptech.ticketshow.data.mappers.RoleMapper;
import com.aptech.ticketshow.data.repositories.AdminRepository;
import com.aptech.ticketshow.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RoleMapper roleMapper;

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
    public AdminDTO create(AdminDTO adminDTO) {
        Admin admin = adminMapper.toEntity(adminDTO);
        admin = adminRepository.save(admin);
        return adminMapper.toDTO(admin);
    }

    @Override
    public AdminDTO update(AdminDTO adminDTO) {
        Long id = adminDTO.getId(); // Lấy id từ adminDTO
        Optional<Admin> adminOptional = adminRepository.findById(id);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            admin.setName(adminDTO.getName());
            admin.setPassword(adminDTO.getPassword());
            admin.setRole(roleMapper.toEntity(adminDTO.getRoleDTO()));
            admin = adminRepository.save(admin);
            return adminMapper.toDTO(admin);
        } else {
            throw new RuntimeException("Admin not found with id: " + id);
        }
    }

    @Override
    public void delete(Long id) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        if (adminOptional.isPresent()) {
            adminRepository.deleteById(id);
        } else {
            throw new RuntimeException("Admin not found with id: " + id);
        }
    }
}
