package com.aptech.ticketshow.data.mappers;

import com.aptech.ticketshow.data.entities.Admin;
import com.aptech.ticketshow.data.dtos.AdminDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        RoleMapper.class
})
public interface AdminMapper {

    Admin toEntity(AdminDTO adminDTO);

    AdminDTO toDTO(Admin admin);
}
