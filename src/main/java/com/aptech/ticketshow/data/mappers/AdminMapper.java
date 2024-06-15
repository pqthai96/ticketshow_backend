package com.aptech.ticketshow.data.mappers;

import com.aptech.ticketshow.data.entities.Admin;
import com.aptech.ticketshow.data.dtos.AdminDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    Admin toEntity(AdminDTO adminDTO);

    AdminDTO toDTO(Admin admin);
}
