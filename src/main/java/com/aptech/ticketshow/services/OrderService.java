package com.aptech.ticketshow.services;

import java.util.List;

import com.aptech.ticketshow.data.dtos.CartDTO;
import com.aptech.ticketshow.data.dtos.OrderDTO;
import com.aptech.ticketshow.data.dtos.request.CheckoutRequest;

public interface OrderService {

  List<OrderDTO> findAll();

  OrderDTO findById(String id);

  List<OrderDTO> findByUserId(Long userId);

  OrderDTO create(OrderDTO orderDTO, CheckoutRequest checkoutRequest);

  OrderDTO update(OrderDTO orderDTO);

  List<OrderDTO> findByVoucherId(Long voucherId);

  String generateOrderCode();

  List<OrderDTO> findByEventIdAndStatusIds(Long eventId, List<Long> statusIds);
}
