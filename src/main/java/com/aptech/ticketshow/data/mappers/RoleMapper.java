package com.aptech.ticketshow.data.mappers;

import com.aptech.ticketshow.data.dtos.RoleDTO;
import com.aptech.ticketshow.data.entities.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleDTO roleDTO);

    RoleDTO toDTO(Role role);
}