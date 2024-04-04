package com.aptech.ticketshow.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.ticketshow.data.dtos.DiscountDTO;
import com.aptech.ticketshow.data.entities.Discount;
import com.aptech.ticketshow.data.mappers.DiscountMapper;
import com.aptech.ticketshow.data.repositories.DiscountRepository;
import com.aptech.ticketshow.services.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService {

	@Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private DiscountMapper discountMapper;

    @Autowired
    private TicketService ticketService;
    
    @Override
    public List<DiscountDTO> findAll() {
        return discountRepository.findAll().stream().map(r -> discountMapper.toDTO(r)).collect(Collectors.toList());
    }

	@Override
	public DiscountDTO create(DiscountDTO discountDTO) {
		discountDTO.setTicketDTO(ticketService.create(discountDTO.getTicketDTO()));
		
		Discount discount = discountMapper.toEntity(discountDTO);
        discount = discountRepository.save(discount);
        return discountMapper.toDTO(discount);
	}

	@Override
	public DiscountDTO getById(Long id) {
		Optional<Discount> optionalDiscount = discountRepository.findById(id);
        if (optionalDiscount.isPresent()) {
            DiscountDTO discountDTO = discountMapper.toDTO(optionalDiscount.get());
            
            discountDTO.setTicketDTO(ticketService.getById(optionalDiscount.get().getTicket().getId()));
            return discountDTO;
        }
        return null;
	}

	@Override
	public DiscountDTO update(Long id, DiscountDTO discountDTO) {
		Optional<Discount> optionalDiscount = discountRepository.findById(id);
        if (optionalDiscount.isPresent()) {
            discountDTO.setTicketDTO(ticketService.update(optionalDiscount.get().getTicket().getId(), discountDTO.getTicketDTO()));

            Discount existingDiscount = optionalDiscount.get();
            existingDiscount = discountMapper.toEntity(discountDTO);
            existingDiscount = discountRepository.save(existingDiscount);
            return discountMapper.toDTO(existingDiscount);
        }
        return null;
	}

	@Override
	public void delete(Long id) {
		discountRepository.deleteById(id);	
	}
}
