package com.aptech.ticketshow.data.mappers;

import com.aptech.ticketshow.data.entities.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aptech.ticketshow.data.dtos.FeedbackDTO;


@Mapper(componentModel = "spring", uses = {
        AdminMapper.class, StatusMapper.class
})
public interface FeedbackMapper {
	
	@Mapping(source = "adminDTO", target = "admin")
    @Mapping(source = "statusDTO", target = "status")
    Feedback toEntity(FeedbackDTO feedbackDTO);

    @Mapping(source = "admin", target = "adminDTO")
    @Mapping(source = "status", target = "statusDTO")
    FeedbackDTO toDTO(Feedback feedback);
}
