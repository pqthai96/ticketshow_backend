package com.aptech.ticketshow.data.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aptech.ticketshow.data.dtos.OrderItemDTO;
import com.aptech.ticketshow.data.entities.OrderItem;

@Mapper(componentModel = "spring", uses = {
    OrderMapper.class, TicketMapper.class, DiscountMapper.class
})
public interface OrderItemMapper {
  @Mapping(source = "orderDTO", target = "order")
  @Mapping(source = "ticketDTO", target = "ticket")
  @Mapping(source = "discountDTO", target = "discount")
  OrderItem toEntity(OrderItemDTO oderItemDTO);

  @Mapping(source = "order", target = "orderDTO")
  @Mapping(source = "ticket", target = "ticketDTO")
  @Mapping(source = "discount", target = "discountDTO")
  OrderItemDTO toDTO(OrderItem orderItem);
}
