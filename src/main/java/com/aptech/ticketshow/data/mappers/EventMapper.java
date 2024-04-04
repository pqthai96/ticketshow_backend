package com.aptech.ticketshow.data.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aptech.ticketshow.data.dtos.EventDTO;
import com.aptech.ticketshow.data.entities.Event;

@Mapper(componentModel = "spring", uses = { AdminMapper.class, OrganiserMapper.class, CategoryMapper.class })
public interface EventMapper {
    @Mapping(source = "categoryDTO", target = "category")
    @Mapping(source = "adminDTO", target = "editedByAdminId")
    @Mapping(source = "organiserDTO", target = "organiser")
    Event toEntity(EventDTO eventDTO);

    @Mapping(source = "category", target = "categoryDTO")
    @Mapping(source = "editedByAdminId", target = "adminDTO")
    @Mapping(source = "organiser", target = "organiserDTO")
    EventDTO toDTO(Event event);
}