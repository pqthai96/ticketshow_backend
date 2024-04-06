package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.AdminDTO;

import java.util.List;

public interface AdminService {

    List<AdminDTO> findAll();

    AdminDTO findById(Long id);

    AdminDTO save(AdminDTO adminDTO);

    AdminDTO update(AdminDTO adminDTO);

    void deleteById(Long id);
}
