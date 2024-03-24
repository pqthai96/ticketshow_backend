package com.aptech.ticketshow.data.mappers;

import com.aptech.ticketshow.data.dtos.UserDTO;
import com.aptech.ticketshow.data.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        RoleMapper.class, StatusMapper.class
})
public interface UserMapper {

    @Mapping(source = "statusDTO", target = "status")
    @Mapping(source = "roleDTO", target = "role")
    User toEntity(UserDTO userDTO);

    @Mapping(source = "status", target = "statusDTO")
    @Mapping(source = "role", target = "roleDTO")
    UserDTO toDTO(User user);
}
