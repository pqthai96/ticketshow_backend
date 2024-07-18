package com.aptech.ticketshow.data.mappers;

import com.aptech.ticketshow.data.dtos.UserProfileDTO;
import com.aptech.ticketshow.data.entities.User;
import com.aptech.ticketshow.data.dtos.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        StatusMapper.class
})
public interface UserMapper {

    @Mapping(source = "statusDTO", target = "status")
    User toEntity(UserDTO userDTO);

    @Mapping(source = "status", target = "statusDTO")
    UserDTO toDTO(User user);

    UserProfileDTO toProfileDTO(User user);
}
