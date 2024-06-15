package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.EventDTO;
import com.aptech.ticketshow.data.entities.Event;
import com.aptech.ticketshow.data.mappers.EventMapper;
import com.aptech.ticketshow.data.repositories.EventRepository;
import com.aptech.ticketshow.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMapper eventMapper;

    @Override
    public List<EventDTO> findAll() {
        return eventRepository.findAll().stream().map(r -> eventMapper.toDTO(r)).collect(Collectors.toList());
    }
    @Override
    public EventDTO getById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return eventMapper.toDTO(event);
    }
}
