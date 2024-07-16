package com.aptech.ticketshow.data.mappers;

import com.aptech.ticketshow.data.dtos.TicketDTO;
import com.aptech.ticketshow.data.entities.Ticket;
import org.mapstruct.Mapper;

import com.aptech.ticketshow.data.dtos.EventDTO;
import com.aptech.ticketshow.data.entities.Event;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "organiser", source = "organiser")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "editedByAdminId", source = "editedByAdminId")
    EventDTO toDTO(Event event);

    @Mapping(target = "organiser", source = "organiser")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "editedByAdminId", source = "editedByAdminId")
    Event toEntity(EventDTO eventDTO);
}
