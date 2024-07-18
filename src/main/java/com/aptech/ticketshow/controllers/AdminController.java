package com.aptech.ticketshow.controllers;

import com.aptech.ticketshow.data.dtos.AdminDTO;
import com.aptech.ticketshow.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<List<AdminDTO>> findAll() {
        List<AdminDTO> admins = adminService.findAll();
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> findById(@PathVariable Long id) {
        AdminDTO admin = adminService.findById(id);
        return ResponseEntity.ok(admin);
    }

    @PostMapping
    public ResponseEntity<AdminDTO> create(@RequestBody AdminDTO adminDTO) {
        AdminDTO createdAdmin = adminService.create(adminDTO);
        return ResponseEntity.created(URI.create("/api/admin/" + createdAdmin.getId())).body(createdAdmin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminDTO> update(@PathVariable Long id, @RequestBody AdminDTO adminDTO) {
        adminDTO.setId(id);
        AdminDTO updatedAdmin = adminService.update(adminDTO);
        return ResponseEntity.ok(updatedAdmin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adminService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
