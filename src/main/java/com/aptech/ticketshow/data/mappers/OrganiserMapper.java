package com.aptech.ticketshow.data.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aptech.ticketshow.data.dtos.OrganiserDTO;
import com.aptech.ticketshow.data.entities.Organiser;

@Mapper(componentModel = "spring", uses={UserMapper.class})
public interface OrganiserMapper {
  @Mapping(source = "userDTO", target = "user")
  Organiser toEntity(OrganiserDTO organiserDTO);

  @Mapping(source = "user", target = "userDTO")
  OrganiserDTO toDTO(Organiser organiser);
}
