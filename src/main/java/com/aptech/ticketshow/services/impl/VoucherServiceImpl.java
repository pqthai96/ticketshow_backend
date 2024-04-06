package com.aptech.ticketshow.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.ticketshow.data.dtos.VoucherDTO;
import com.aptech.ticketshow.data.entities.Voucher;
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

	@Override
	public VoucherDTO create(VoucherDTO voucherDTO) {
		Voucher voucher = voucherMapper.toEntity(voucherDTO);
        voucher = voucherRepository.save(voucher);
        return voucherMapper.toDTO(voucher);
	}

	@Override
	public VoucherDTO getById(Long id) {
		Optional<Voucher> optionalVoucher = voucherRepository.findById(id);
        if (optionalVoucher.isPresent()) {
            return voucherMapper.toDTO(optionalVoucher.get());
        }
        return null;
	}

	@Override
	public VoucherDTO update(VoucherDTO voucherDTO) {
		long id = voucherDTO.getId();
		Optional<Voucher> optionalVoucher = voucherRepository.findById(id);
        if (optionalVoucher.isPresent()) {
            Voucher existingVoucher = optionalVoucher.get();
            existingVoucher = voucherMapper.toEntity(voucherDTO);
            existingVoucher = voucherRepository.save(existingVoucher);
            return voucherMapper.toDTO(existingVoucher);
        }
        return null;
	}

	@Override
	public void delete(Long id) {
		 voucherRepository.deleteById(id);
		
	}
}
