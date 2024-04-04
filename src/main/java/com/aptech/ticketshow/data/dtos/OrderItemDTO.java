package com.aptech.ticketshow.data.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class OrderItemDTO {
  private Long id;

  private Integer quantity;

  private OrderDTO orderDTO;

  private TicketDTO ticketDTO;

  private DiscountDTO discountDTO;

  private Date createdAt;

  private Date updatedAt;
}
