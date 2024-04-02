package com.aptech.ticketshow.data.mappers;

import com.aptech.ticketshow.data.dtos.AdminDTO;
import com.aptech.ticketshow.data.dtos.BankDTO;
import com.aptech.ticketshow.data.dtos.EventDTO;
import com.aptech.ticketshow.data.entities.Admin;
import com.aptech.ticketshow.data.entities.Bank;
import com.aptech.ticketshow.data.entities.Event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        UserMapper.class
})
public interface EventMapper {

    Event toEntity(EventDTO eventDTO);

    EventDTO toDTO(Event event);
}
