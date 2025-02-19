package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.StatusDTO;
import com.aptech.ticketshow.data.entities.Status;
import com.aptech.ticketshow.data.mappers.StatusMapper;
import com.aptech.ticketshow.data.repositories.StatusRepository;
import com.aptech.ticketshow.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private StatusMapper statusMapper;

    @Override
    public StatusDTO findById(Long id) {
        Optional<Status> status = statusRepository.findById(id);
        return status.map(value -> statusMapper.toDTO(value)).orElse(null);
    }
}
