package com.aptech.ticketshow.data.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aptech.ticketshow.data.dtos.OrderItemDTO;
import com.aptech.ticketshow.data.entities.OrderItem;

@Mapper(componentModel = "spring", uses = {
    OrderMapper.class, TicketMapper.class
})
public interface OrderItemMapper {
  @Mapping(source = "orderDTO", target = "order")
  @Mapping(source = "ticketDTO", target = "ticket")
  OrderItem toEntity(OrderItemDTO oderItemDTO);

  @Mapping(source = "order", target = "orderDTO")
  @Mapping(source = "ticket", target = "ticketDTO")
  OrderItemDTO toDTO(OrderItem orderItem);
}
