package com.aptech.ticketshow.data.dtos;

import lombok.Data;

@Data
public class OrderItemDTO {
  private Long id;

  private Integer quantity;

  private String seatValue;

  private OrderDTO orderDTO;

  private TicketDTO ticketDTO;

  private String qrCodeBase64;
}
