package com.aptech.ticketshow.services;

import java.util.List;

import com.aptech.ticketshow.data.dtos.CartDTO;
import com.aptech.ticketshow.data.dtos.OrderDTO;
import com.aptech.ticketshow.data.dtos.request.CheckoutRequest;

public interface OrderService {

  List<OrderDTO> findAll();

  OrderDTO findById(Long id);

  List<OrderDTO> findByUserId(Long userId);

  OrderDTO create(OrderDTO orderDTO, CheckoutRequest checkoutRequest);

  OrderDTO update(OrderDTO orderDTO);

  void delete(Long id);
}
