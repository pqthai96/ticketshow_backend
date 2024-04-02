package com.aptech.ticketshow.data.mappers;

import com.aptech.ticketshow.data.dtos.AdminDTO;
import com.aptech.ticketshow.data.dtos.BankDTO;
import com.aptech.ticketshow.data.dtos.TicketDTO;
import com.aptech.ticketshow.data.entities.Admin;
import com.aptech.ticketshow.data.entities.Bank;
import com.aptech.ticketshow.data.entities.Ticket;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        UserMapper.class
})
public interface TicketMapper {

    Ticket toEntity(TicketDTO ticketDTO);

    TicketDTO toDTO(Ticket ticket);
}
