package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.TicketDTO;

import java.util.List;

public interface TicketService {

    List<TicketDTO> findAll();

    TicketDTO findByID(Long id);

    TicketDTO addTicket(TicketDTO ticketDTO);

    void deleteTicket(Long id);

    TicketDTO updateTicket(TicketDTO ticketDTO);
}
