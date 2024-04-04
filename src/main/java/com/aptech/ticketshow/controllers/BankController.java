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
    public ResponseEntity<List<BankDTO>> getBanks() {
        List<BankDTO> banks = bankService.findAll();
        return ResponseEntity.ok(banks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankDTO> getBankById(@PathVariable Long id) {
        BankDTO bank = bankService.findById(id);
        return ResponseEntity.ok(bank);
    }

    @PostMapping
    public ResponseEntity<BankDTO> createBank(@RequestBody BankDTO bankDTO) {
        BankDTO createdBank = bankService.save(bankDTO);
        return ResponseEntity.created(URI.create("/api/bank/" + createdBank.getId())).body(createdBank);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankDTO> updateBank(@PathVariable Long id, @RequestBody BankDTO bankDTO) {
        BankDTO updatedBank = bankService.update(id, bankDTO);
        return ResponseEntity.ok(updatedBank);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankById(@PathVariable Long id) {
        bankService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
