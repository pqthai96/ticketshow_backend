package com.aptech.ticketshow.data.mappers;

import com.aptech.ticketshow.data.entities.Voucher;
import org.mapstruct.Mapper;

import com.aptech.ticketshow.data.dtos.VoucherDTO;

@Mapper(componentModel = "spring")
public interface VoucherMapper {
	
	 Voucher toEntity(VoucherDTO voucherDTO);

	 VoucherDTO toDTO(Voucher voucher);
}
