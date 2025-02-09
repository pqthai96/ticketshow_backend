package com.aptech.ticketshow.data.dtos;

import lombok.Data;

@Data
public class OrderItemDTO {
  private Long id;

  private Integer quantity;

  private OrderDTO orderDTO;

  private TicketDTO ticketDTO;
}
