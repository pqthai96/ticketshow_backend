package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.EventDTO;
import com.aptech.ticketshow.data.dtos.EventFilterDTO;
import com.aptech.ticketshow.data.dtos.PaginationDTO;
import com.aptech.ticketshow.data.entities.Event;
import com.aptech.ticketshow.data.mappers.EventMapper;
import com.aptech.ticketshow.data.repositories.EventRepository;
import com.aptech.ticketshow.data.repositories.specification.EventSpecification;
import com.aptech.ticketshow.services.EventService;
import com.aptech.ticketshow.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private TicketService ticketService;

    @Override
    public PaginationDTO findAll(int no, int limit) {

        Page<EventDTO> page = eventRepository.findAll(PageRequest.of(no, limit)).map(r -> eventMapper.toDTO(r));

        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(), page.getTotalPages(), page.getTotalElements(), page.getSize(), page.getNumber());
    }

    @Override
    public EventDTO findById(Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            EventDTO eventDTO = eventMapper.toDTO(eventOptional.get());
            eventDTO.setTickets(ticketService.findByEventId(eventDTO.getId()));

            return eventDTO;
        } else {
            throw new RuntimeException("Event not found with id: " + id);
        }
    }

    @Override
    public EventDTO update(EventDTO eventDTO) {
        return eventMapper.toDTO(eventRepository.save(eventMapper.toEntity(eventDTO)));
    }

    @Override
    public PaginationDTO filter(int no, int limit, EventFilterDTO eventFilterDTO) {
        Page<EventDTO> page = eventRepository.findAll(EventSpecification.filterEvent(eventFilterDTO), PageRequest.of(no, limit))
                .map(item -> eventMapper.toDTO(item));

        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(), page.getTotalPages(), page.getTotalElements(), page.getSize(), page.getNumber());
    }

    @Override
    public PaginationDTO search(int no, int limit, String searchValue) {
        Page<EventDTO> page = eventRepository.search(searchValue, PageRequest.of(no, limit)).map(r -> eventMapper.toDTO(r));

        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(), page.getTotalPages(), page.getTotalElements(), page.getSize(), page.getNumber());
    }

    @Override
    public EventDTO bookedSeat(EventDTO eventDTO) {
        Optional<Event> eventOptional = eventRepository.findById(eventDTO.getId());
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            event.setBookedSeats(eventDTO.getBookedSeats());

            event = eventRepository.save(event);
            return eventMapper.toDTO(event);
        } else {
            throw new RuntimeException("Event not found with id: " + eventDTO.getId());
        }
    }
}
