package com.aptech.ticketshow.services;

import java.util.List;

import com.aptech.ticketshow.data.dtos.OrderDTO;

public interface OrderService {
  List<OrderDTO> findAll();

  OrderDTO findById(Long id);

  OrderDTO create(OrderDTO orderItemDTO);

  OrderDTO update(OrderDTO orderItemDTO);

  void delete(Long id);
}
