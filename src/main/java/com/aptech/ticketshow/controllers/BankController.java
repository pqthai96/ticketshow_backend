package com.aptech.ticketshow.controllers;

import com.aptech.ticketshow.data.dtos.BankDTO;
import com.aptech.ticketshow.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/bank")
public class BankController {

    @Autowired
    private BankService bankService;

    @GetMapping
    public ResponseEntity<List<BankDTO>> findAll() {
        List<BankDTO> banks = bankService.findAll();
        return ResponseEntity.ok(banks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankDTO> findById(@PathVariable Long id) {
        BankDTO bank = bankService.findById(id);
        return ResponseEntity.ok(bank);
    }

    @PostMapping
    public ResponseEntity<BankDTO> create(@RequestBody BankDTO bankDTO) {
        BankDTO createdBank = bankService.create(bankDTO);
        return ResponseEntity.created(URI.create("/api/bank/" + createdBank.getId())).body(createdBank);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankDTO> update(@RequestBody BankDTO bankDTO) {
        BankDTO updatedBank = bankService.update(bankDTO);
        return ResponseEntity.ok(updatedBank);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bankService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
