package com.aptech.ticketshow.data.mappers;

import org.mapstruct.Mapper;

import com.aptech.ticketshow.data.dtos.EventDTO;
import com.aptech.ticketshow.data.entities.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {

    Event toEntity(EventDTO eventDTO);

    EventDTO toDTO(Event event);
}
