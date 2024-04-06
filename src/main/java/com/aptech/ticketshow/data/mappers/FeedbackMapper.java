package com.aptech.ticketshow.data.mappers;

import com.aptech.ticketshow.data.entities.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aptech.ticketshow.data.dtos.FeedbackDTO;


@Mapper(componentModel = "spring", uses = {
        AdminMapper.class
})
public interface FeedbackMapper {
	
	@Mapping(source = "adminDTO", target = "admin")
    Feedback toEntity(FeedbackDTO feedbackDTO);

    @Mapping(source = "admin", target = "adminDTO")
    FeedbackDTO toDTO(Feedback feedback);
}
