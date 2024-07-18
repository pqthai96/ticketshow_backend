package com.aptech.ticketshow.data.mappers;

import com.aptech.ticketshow.data.dtos.OrganiserDTO;
import com.aptech.ticketshow.data.entities.Organiser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses={UserMapper.class})
public interface OrganiserMapper {
    @Mapping(target = "user", source = "user")
    @Mapping(target = "banks", source = "banks")
    @Mapping(target = "events", source = "events")
    OrganiserDTO toDTO(Organiser organiser);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "banks", source = "banks")
    @Mapping(target = "events", source = "events")
    Organiser toEntity(OrganiserDTO organiserDTO);
}
