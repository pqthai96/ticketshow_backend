package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.RoleDTO;
import com.aptech.ticketshow.data.dtos.TicketDTO;
import com.aptech.ticketshow.data.entities.Role;
import com.aptech.ticketshow.data.mappers.RoleMapper;
import com.aptech.ticketshow.data.mappers.TicketMapper;
import com.aptech.ticketshow.data.repositories.RoleRepository;
import com.aptech.ticketshow.data.repositories.TicketRepository;
import com.aptech.ticketshow.services.RoleService;
import com.aptech.ticketshow.services.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public List<TicketDTO> findAll() {
        return ticketRepository.findAll().stream().map(r -> ticketMapper.toDTO(r)).collect(Collectors.toList());
    }
}
