package com.aptech.ticketshow.services;

import java.util.List;

import com.aptech.ticketshow.data.dtos.DiscountDTO;

public interface DiscountService {
	
	List<DiscountDTO> findAll();
	
    DiscountDTO create(DiscountDTO discountDTO);

    DiscountDTO findById(Long id);

    DiscountDTO update(DiscountDTO discountDTO);

    void delete(Long id);
}
