package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.StatusDTO;

import java.util.List;

public interface StatusService {

    StatusDTO findById(Long id);

    List<StatusDTO> findAll();
}
