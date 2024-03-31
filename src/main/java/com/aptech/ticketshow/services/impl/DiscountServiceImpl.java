package com.aptech.ticketshow.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.ticketshow.data.dtos.DiscountDTO;
import com.aptech.ticketshow.data.mappers.DiscountMapper;
import com.aptech.ticketshow.data.repositories.DiscountRepository;
import com.aptech.ticketshow.services.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService {

	@Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private DiscountMapper discountMapper;

    @Override
    public List<DiscountDTO> findAll() {
        return discountRepository.findAll().stream().map(r -> discountMapper.toDTO(r)).collect(Collectors.toList());
    }
}
