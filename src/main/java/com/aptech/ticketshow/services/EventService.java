package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.EventDTO;
import com.aptech.ticketshow.data.dtos.TicketDTO;

import java.util.List;

public interface EventService {

    List<EventDTO> findAll();

    EventDTO findByID(Long id);

    EventDTO addEvent(EventDTO eventDTO);

    void deleteEvent(Long id);

    EventDTO updateEvent(EventDTO eventDTO);
}
