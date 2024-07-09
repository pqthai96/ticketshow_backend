package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.EventDTO;
import com.aptech.ticketshow.data.dtos.TicketDTO;
import com.aptech.ticketshow.data.entities.Event;

import java.util.List;

public interface EventService {

    List<EventDTO> findAll();
    List<EventDTO> getListFilter(EventDTO eventDTO);
    EventDTO findByID(Long id);

    EventDTO addEvent(EventDTO eventDTO);

    void deleteEvent(Long id);

    EventDTO updateEvent(EventDTO eventDTO);
    EventDTO bookedSeat(EventDTO eventDTO);

}
