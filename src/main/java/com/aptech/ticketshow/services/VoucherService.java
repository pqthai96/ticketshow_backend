package com.aptech.ticketshow.services;

import java.util.List;

import com.aptech.ticketshow.data.dtos.VoucherDTO;

public interface VoucherService {
	
	List<VoucherDTO> findAll();
	
    VoucherDTO create(VoucherDTO voucherDTO);

    VoucherDTO findById(Long id);

    VoucherDTO update(VoucherDTO voucherDTO);

    void delete(Long id);

    VoucherDTO findByCode(String voucherCode);
}
