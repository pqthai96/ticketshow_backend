package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.OrganiserDTO;
import com.aptech.ticketshow.data.entities.Organiser;
import com.aptech.ticketshow.data.mappers.OrganiserMapper;
import com.aptech.ticketshow.data.mappers.UserMapper;
import com.aptech.ticketshow.data.repositories.OrganiserRepository;
import com.aptech.ticketshow.services.OrganiserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrganiserServiceImpl implements OrganiserService {

    @Autowired
    private OrganiserRepository organizerRepository;

    @Autowired
    private OrganiserMapper organizerMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<OrganiserDTO> findAll() {
        return organizerRepository.findAll().stream()
                .map(organizerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrganiserDTO findById(Long id) {
        Optional<Organiser> organizerOptional = organizerRepository.findById(id);
        if (organizerOptional.isPresent()) {
            return organizerMapper.toDTO(organizerOptional.get());
        } else {
            throw new RuntimeException("Organiser not found with id: " + id);
        }
    }

    @Override
    public OrganiserDTO save(OrganiserDTO organizerDTO) {
        Organiser organizer = organizerMapper.toEntity(organizerDTO);
        organizer = organizerRepository.save(organizer);
        return organizerMapper.toDTO(organizer);
    }

    @Override
    public OrganiserDTO update(OrganiserDTO organizerDTO) {
        Long id = organizerDTO.getId();
        Optional<Organiser> organizerOptional = organizerRepository.findById(id);
        if (organizerOptional.isPresent()) {
            Organiser organizer = organizerOptional.get();
            organizer = organizerRepository.save(organizer);
            return organizerMapper.toDTO(organizer);
        } else {
            throw new RuntimeException("Organiser not found with id: " + id);
        }
    }

    @Override
    public void deleteById(Long id) {
        Optional<Organiser> organizerOptional = organizerRepository.findById(id);
        if (organizerOptional.isPresent()) {
            organizerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Organiser not found with id: " + id);
        }
    }
}
