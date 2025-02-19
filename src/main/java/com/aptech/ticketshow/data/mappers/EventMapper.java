package com.aptech.ticketshow.data.mappers;

import com.aptech.ticketshow.data.dtos.TicketDTO;
import com.aptech.ticketshow.data.entities.Organiser;
import com.aptech.ticketshow.data.entities.Ticket;
import org.mapstruct.Mapper;

import com.aptech.ticketshow.data.dtos.EventDTO;
import com.aptech.ticketshow.data.entities.Event;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        StatusMapper.class, OrganiserMapper.class, CategoryMapper.class
})
public interface EventMapper {

    @Mapping(source = "organiserDTO", target = "organiser")
    @Mapping(source = "categoryDTO", target = "category")
    @Mapping(source = "statusDTO", target = "status")
    Event toEntity(EventDTO eventDTO);

    @Mapping(source = "organiser", target = "organiserDTO")
    @Mapping(source = "category", target = "categoryDTO")
    @Mapping(source = "status", target = "statusDTO")
    EventDTO toDTO(Event event);
}
