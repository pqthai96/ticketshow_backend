package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.RoleDTO;
import com.aptech.ticketshow.data.entities.Role;
import com.aptech.ticketshow.data.mappers.RoleMapper;
import com.aptech.ticketshow.data.repositories.RoleRepository;
import com.aptech.ticketshow.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class EventServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<RoleDTO> findAll() {
        return roleRepository.findAll().stream().map(r -> roleMapper.toDTO(r)).collect(Collectors.toList());
    }
}
