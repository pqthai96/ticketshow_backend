package com.aptech.ticketshow.services;

import java.util.List;

import com.aptech.ticketshow.data.dtos.DiscountDTO;

public interface DiscountService {
	
	List<DiscountDTO> findAll();
}
