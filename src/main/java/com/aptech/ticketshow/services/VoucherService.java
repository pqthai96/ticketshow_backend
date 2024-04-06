package com.aptech.ticketshow.services;

import java.util.List;

import com.aptech.ticketshow.data.dtos.VoucherDTO;

public interface VoucherService {
	
	List<VoucherDTO> findAll();
	
    VoucherDTO create(VoucherDTO voucherDTO);

    VoucherDTO getById(Long id);

    VoucherDTO update(Long id, VoucherDTO voucherDTO);

    void delete(Long id);
}