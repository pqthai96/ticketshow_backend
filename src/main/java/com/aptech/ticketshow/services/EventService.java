package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.EventDTO;
import com.aptech.ticketshow.data.dtos.EventFilterDTO;
import com.aptech.ticketshow.data.dtos.PaginationDTO;
import com.aptech.ticketshow.data.dtos.TicketDTO;

import java.util.List;

public interface EventService {

    PaginationDTO findAll(int no, int limit);

    EventDTO findById(Long id);

    EventDTO create(EventDTO eventDTO);

    void delete(Long id);

    EventDTO update(EventDTO eventDTO);

    PaginationDTO filter(int no, int limit, EventFilterDTO eventFilterDTO);

    PaginationDTO search(int no, int limit, String searchValue);
}
