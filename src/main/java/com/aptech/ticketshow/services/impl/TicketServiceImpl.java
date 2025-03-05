package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.OrderDTO;
import com.aptech.ticketshow.data.dtos.TicketDTO;
import com.aptech.ticketshow.data.entities.OrderItem;
import com.aptech.ticketshow.data.entities.Ticket;
import com.aptech.ticketshow.data.mappers.AdminMapper;
import com.aptech.ticketshow.data.mappers.TicketMapper;
import com.aptech.ticketshow.data.repositories.OrderItemRepository;
import com.aptech.ticketshow.data.repositories.TicketRepository;
import com.aptech.ticketshow.services.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<TicketDTO> findAll() {
        return ticketRepository.findAll().stream().map(r -> ticketMapper.toDTO(r)).collect(Collectors.toList());
    }

    @Override
    public TicketDTO findById(Long id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isPresent()) {
            return ticketMapper.toDTO(ticketOptional.get());
        } else {
            throw new RuntimeException("Ticket not found with id: " + id);
        }
    }

    @Override
    public List<TicketDTO> findByEventId(Long eventId) {
        return ticketRepository.findByEventId(eventId).stream().map(r -> ticketMapper.toDTO(r)).collect(Collectors.toList());
    }

    @Override
    public TicketDTO create(TicketDTO ticketDTO) {
        Ticket ticket = ticketMapper.toEntity(ticketDTO);
        ticket = ticketRepository.save(ticket);
        return ticketMapper.toDTO(ticket);
    }

    @Override
    public void delete(Long id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isPresent()) {
            ticketRepository.deleteById(id);
        } else {
            throw new RuntimeException("Ticket not found with id: " + id);
        }
    }

    @Override
    public TicketDTO update(TicketDTO ticketDTO) {
        return ticketMapper.toDTO(ticketRepository.save(ticketMapper.toEntity(ticketDTO)));
    }

    @Override
    public int getTicketsBookedCount(Long ticketId) {
        List<OrderItem> orderItems = orderItemRepository.findByTicketIdAndOrderStatusCompleted(ticketId);

        if (orderItems.isEmpty()) {
            return 0;
        }

        int bookedTicketCount = 0;
        for (OrderItem orderItem : orderItems) {
            bookedTicketCount = bookedTicketCount + orderItem.getQuantity();
        }

        return bookedTicketCount;
    }
}
