package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.OrderItemDTO;
import com.aptech.ticketshow.data.mappers.OrderItemMapper;
import com.aptech.ticketshow.data.repositories.OrderItemRepository;
import com.aptech.ticketshow.services.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItemDTO> findByOrderId(String orderId) {
        return orderItemRepository.findByOrderId(orderId).stream().map(r -> orderItemMapper.toDTO(r)).collect(Collectors.toList());
    }

    @Override
    public OrderItemDTO save(OrderItemDTO orderItemDTO) {
        return orderItemMapper.toDTO(orderItemRepository.save(orderItemMapper.toEntity(orderItemDTO)));
    }
}
