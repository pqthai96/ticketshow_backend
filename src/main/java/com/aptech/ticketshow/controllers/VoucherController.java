package com.aptech.ticketshow.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aptech.ticketshow.data.dtos.VoucherDTO;
import com.aptech.ticketshow.services.VoucherService;

@RestController
@RequestMapping("api/voucher")
public class VoucherController {
	
	@Autowired
	private VoucherService voucherService;
	@GetMapping
    public ResponseEntity<List<VoucherDTO>> findAll() {
        List<VoucherDTO> vouchers = voucherService.findAll();
        return new ResponseEntity<>(vouchers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherDTO> findById(@PathVariable("id") Long id) {
        VoucherDTO voucherDTO = voucherService.findById(id);
        if (voucherDTO != null) {
            return new ResponseEntity<>(voucherDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<VoucherDTO> create(@RequestBody VoucherDTO voucherDTO) {
        VoucherDTO createdVoucher = voucherService.create(voucherDTO);
        return new ResponseEntity<>(createdVoucher, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoucherDTO> update(@PathVariable("id") Long id, @RequestBody VoucherDTO voucherDTO) {
    	voucherDTO.setId(id);
        VoucherDTO updatedVoucher = voucherService.update(voucherDTO);
        if (updatedVoucher != null) {
            return new ResponseEntity<>(updatedVoucher, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        voucherService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
