package com.aptech.ticketshow.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.ticketshow.data.dtos.VoucherDTO;
import com.aptech.ticketshow.data.mappers.VoucherMapper;
import com.aptech.ticketshow.data.repositories.VoucherRepository;
import com.aptech.ticketshow.services.VoucherService;

@Service
public class VoucherServiceImpl implements VoucherService{
	@Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private VoucherMapper voucherMapper;

    @Override
    public List<VoucherDTO> findAll() {
        return voucherRepository.findAll().stream().map(r -> voucherMapper.toDTO(r)).collect(Collectors.toList());
    }
}
