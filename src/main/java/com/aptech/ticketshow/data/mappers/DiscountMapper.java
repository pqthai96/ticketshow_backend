package com.aptech.ticketshow.data.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aptech.ticketshow.data.dtos.DiscountDTO;
import com.aptech.ticketshow.data.entities.Discount;

@Mapper(componentModel = "spring", uses = {
        TicketMapper.class
})
public interface DiscountMapper {

	@Mapping(source = "ticketDTO", target = "ticket")
	Discount toEntity(DiscountDTO discountDTO);
	
	@Mapping(source = "ticket", target = "ticketDTO")
	DiscountDTO toDTO(Discount discount);
}