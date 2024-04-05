package com.aptech.ticketshow.data.mappers;

import com.aptech.ticketshow.data.dtos.TicketDTO;
import com.aptech.ticketshow.data.entities.Ticket;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        UserMapper.class
})
public interface TicketMapper {

    Ticket toEntity(TicketDTO ticketDTO);

    TicketDTO toDTO(Ticket ticket);
}
