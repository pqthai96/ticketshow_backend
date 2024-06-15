package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.EventDTO;
import com.aptech.ticketshow.data.entities.Event;

import java.util.List;

public interface EventService {

    List<EventDTO> findAll();
    EventDTO getById(Long id);
}
