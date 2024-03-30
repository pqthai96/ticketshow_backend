package com.aptech.ticketshow.data.mappers;

import com.aptech.ticketshow.data.dtos.AdminDTO;
import com.aptech.ticketshow.data.dtos.UserDTO;
import com.aptech.ticketshow.data.entities.Admin;
import com.aptech.ticketshow.data.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        RoleMapper.class
})
public interface AdminMapper {

    @Mapping(source = "roleDTO", target = "role")
    Admin toEntity(AdminDTO adminDTO);

    @Mapping(source = "role", target = "roleDTO")
    AdminDTO toDTO(Admin admin);
}
