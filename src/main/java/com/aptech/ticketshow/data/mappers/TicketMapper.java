package com.aptech.ticketshow.data.mappers;

import com.aptech.ticketshow.data.dtos.TicketDTO;
import com.aptech.ticketshow.data.entities.Ticket;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        EventMapper.class
})
public interface TicketMapper {

    @Mapping(source = "eventDTO", target = "event")
    Ticket toEntity(TicketDTO ticketDTO);

    @Mapping(source = "event", target = "eventDTO")
    TicketDTO toDTO(Ticket ticket);
}
