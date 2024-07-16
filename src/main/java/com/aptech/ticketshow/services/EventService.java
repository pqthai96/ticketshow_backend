package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.EventDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EventService {

    List<EventDTO> findAll();

    EventDTO findByID(Long id);

    //EventDTO addEvent(EventDTO eventDTO);

    void addEvent(EventDTO eventDTO, MultipartFile bannerImg, MultipartFile positionImg);

    void deleteEvent(Long id);

    EventDTO updateEvent(EventDTO eventDTO);
}
