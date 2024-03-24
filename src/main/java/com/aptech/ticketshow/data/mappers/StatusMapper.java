package com.aptech.ticketshow.data.mappers;

import com.aptech.ticketshow.data.dtos.StatusDTO;
import com.aptech.ticketshow.data.entities.Status;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusMapper {
    Status toEntity(StatusDTO statusDTO);

    StatusDTO toDTO(Status status);
}
