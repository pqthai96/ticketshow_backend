package com.aptech.ticketshow.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.aptech.ticketshow.data.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.ticketshow.data.dtos.DiscountDTO;
import com.aptech.ticketshow.data.entities.Discount;
import com.aptech.ticketshow.data.entities.Ticket;
import com.aptech.ticketshow.data.mappers.DiscountMapper;
import com.aptech.ticketshow.data.repositories.DiscountRepository;
import com.aptech.ticketshow.services.DiscountService;
import com.aptech.ticketshow.services.TicketService;

@Service
public class DiscountServiceImpl implements DiscountService {

	@Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private DiscountMapper discountMapper;

    
    @Override
    public List<DiscountDTO> findAll() {
        return discountRepository.findAll().stream().map(r -> discountMapper.toDTO(r)).collect(Collectors.toList());
    }

	@Override
	public DiscountDTO create(DiscountDTO discountDTO) {
		Discount discount = discountMapper.toEntity(discountDTO);

	    Optional<Ticket> optionalTicket = ticketRepository.findById(discountDTO.getTicketDTO().getId());
	    if (optionalTicket.isPresent()) {
	        Ticket ticket = optionalTicket.get();
	        discount.setTicket(ticket);
	    } else {
	        
	    }

	    discount = discountRepository.save(discount);
	    return discountMapper.toDTO(discount);
	}

	@Override
	public DiscountDTO findById(Long id) {
		Optional<Discount> optionalDiscount = discountRepository.findById(id);
        return optionalDiscount.map(discount -> discountMapper.toDTO(discount)).orElse(null);
    }

	@Override
	public DiscountDTO update(DiscountDTO discountDTO) {
		long id = discountDTO.getId();
		Optional<Discount> optionalDiscount = discountRepository.findById(id);
        if (optionalDiscount.isPresent()) {
        	optionalDiscount.get().setTicket(ticketRepository.findById(discountDTO.getTicketDTO().getId()).orElse(null));
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
