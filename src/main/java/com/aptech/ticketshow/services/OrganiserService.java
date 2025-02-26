package com.aptech.ticketshow.services;

import java.util.List;

import com.aptech.ticketshow.data.dtos.OrganiserDTO;

public interface OrganiserService {

    List<OrganiserDTO> findAll();

    OrganiserDTO findById(Long id);

    OrganiserDTO save(OrganiserDTO organizerDTO);

    OrganiserDTO update(OrganiserDTO organizerDTO);

    void deleteById(Long id);
}
