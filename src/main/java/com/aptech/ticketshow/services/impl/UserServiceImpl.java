package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.UserDTO;
import com.aptech.ticketshow.data.entities.User;
import com.aptech.ticketshow.data.mappers.UserMapper;
import com.aptech.ticketshow.data.repositories.UserRepository;
import com.aptech.ticketshow.exception_v2.ResourceNotFoundExceptionV2;
import com.aptech.ticketshow.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(r -> userMapper.toDTO(r)).collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id) {
        return userMapper.toDTO(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExceptionV2(Collections.singletonMap("id", id))));
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        return userMapper.toDTO(userRepository.save(userMapper.toEntity(userDTO)));
    }

    public UserDTO update(UserDTO userDTO) {
        User oldUser = userRepository.findById(userDTO.getId()).orElseThrow(() -> new ResourceNotFoundExceptionV2(Collections.singletonMap("id", userDTO.getId())));

        return null;
    }
}
