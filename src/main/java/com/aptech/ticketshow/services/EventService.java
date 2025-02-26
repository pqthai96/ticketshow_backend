package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.EventDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.aptech.ticketshow.data.dtos.EventFilterDTO;
import com.aptech.ticketshow.data.dtos.PaginationDTO;
import com.aptech.ticketshow.data.dtos.TicketDTO;

import java.util.List;

public interface EventService {

    PaginationDTO findAll(int no, int limit);

    PaginationDTO findAllByStatus(int no, int limit, Long statusId);

    EventDTO findById(Long id);

    //EventDTO addEvent(EventDTO eventDTO);

    EventDTO update(EventDTO eventDTO);

    PaginationDTO filter(int no, int limit, EventFilterDTO eventFilterDTO);

    PaginationDTO search(int no, int limit, String searchValue);

    EventDTO bookedSeat(EventDTO eventDTO);
}
