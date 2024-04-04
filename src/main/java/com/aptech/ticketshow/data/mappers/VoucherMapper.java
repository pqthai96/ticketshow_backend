package com.aptech.ticketshow.data.mappers;

import org.mapstruct.Mapper;

import com.aptech.ticketshow.data.dtos.VoucherDTO;
import com.aptech.ticketshow.data.entities.Voucher;

@Mapper(componentModel = "spring")
public interface VoucherMapper {
  Voucher toEntity(VoucherDTO voucherDTO);

  VoucherDTO toDTO(Voucher voucher);
}
