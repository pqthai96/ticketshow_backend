package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.EventDTO;
import java.util.List;

public interface EventService {

    List<EventDTO> findAll();
}
