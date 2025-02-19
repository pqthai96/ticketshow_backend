package com.aptech.ticketshow.data.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aptech.ticketshow.data.dtos.OrderDTO;
import com.aptech.ticketshow.data.entities.Order;

@Mapper(componentModel = "spring", uses = {
    UserMapper.class, EventMapper.class, VoucherMapper.class, StatusMapper.class
})
public interface OrderMapper {
  @Mapping(source = "userDTO", target = "user")
  @Mapping(source = "eventDTO", target = "event")
  @Mapping(source = "voucherDTO", target = "voucher")
  @Mapping(source = "statusDTO", target = "status")
  Order toEntity(OrderDTO oderDTO);

  @Mapping(source = "user", target = "userDTO")
  @Mapping(source = "event", target = "eventDTO")
  @Mapping(source = "voucher", target = "voucherDTO")
  @Mapping(source = "status", target = "statusDTO")
  OrderDTO toDTO(Order order);
}
