package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.TicketDTO;

import java.util.List;

public interface TicketService {

    List<TicketDTO> findAll();

    TicketDTO findById(Long id);

    List<TicketDTO> findByEventId(Long eventId);

    TicketDTO create(TicketDTO ticketDTO);

    void delete(Long id);

    TicketDTO update(TicketDTO ticketDTO);

    int getTicketsBookedCount(Long ticketId);
}
