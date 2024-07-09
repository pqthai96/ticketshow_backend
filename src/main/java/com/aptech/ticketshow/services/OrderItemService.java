package com.aptech.ticketshow.services;

import java.util.List;

import com.aptech.ticketshow.data.dtos.OrderItemDTO;

public interface OrderItemService {
  List<OrderItemDTO> findAll();

  OrderItemDTO findById(Long id);

  OrderItemDTO create(OrderItemDTO orderItemDTO);

  OrderItemDTO update(OrderItemDTO orderItemDTO);

  void delete(Long id);
}
