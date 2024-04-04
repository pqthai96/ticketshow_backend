package com.aptech.ticketshow.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.ticketshow.data.dtos.DiscountDTO;
import com.aptech.ticketshow.services.DiscountService;

@RestController
@RequestMapping("api/discount")
public class DiscountController {
	@Autowired
	private DiscountService discountService;
	@GetMapping
    public ResponseEntity<List<DiscountDTO>> getAllDiscounts() {
        List<DiscountDTO> discounts = discountService.findAll();
        return new ResponseEntity<>(discounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountDTO> getDiscountById(@PathVariable("id") Long id) {
        DiscountDTO discountDTO = discountService.getById(id);
        if (discountDTO != null) {
            return new ResponseEntity<>(discountDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<DiscountDTO> createDiscount(@RequestBody DiscountDTO discountDTO) {
    	DiscountDTO createdDiscount = discountService.create(discountDTO);
        return new ResponseEntity<>(createdDiscount, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscountDTO> updateDiscount(@PathVariable("id") Long id, @RequestBody DiscountDTO discountDTO) {
        DiscountDTO updatedDiscount = discountService.update(id, discountDTO);
        if (updatedDiscount != null) {
            return new ResponseEntity<>(updatedDiscount, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable("id") Long id) {
    	discountService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
