package com.aptech.ticketshow.services;

import java.util.List;

import com.aptech.ticketshow.data.dtos.OrderItemDTO;

public interface OrderItemService {

  List<OrderItemDTO> findByOrderId(String orderId);

  OrderItemDTO save(OrderItemDTO orderItemDTO);
}
