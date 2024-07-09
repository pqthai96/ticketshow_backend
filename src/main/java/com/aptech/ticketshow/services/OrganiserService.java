package com.aptech.ticketshow.services;

import java.util.List;

import com.aptech.ticketshow.data.dtos.OrganiserDTO;

public interface OrganiserService {
  List<OrganiserDTO> findAll();

  OrganiserDTO findById(Long id);

  OrganiserDTO create(OrganiserDTO organiserDTO);

  OrganiserDTO update(OrganiserDTO organiserDTO);

  void delete(Long id);
}
