package com.aptech.ticketshow.services;

import java.util.List;

import com.aptech.ticketshow.data.dtos.VoucherDTO;

public interface VoucherService {
	
	List<VoucherDTO> findAll();
}
