package com.aptech.ticketshow.controllers;

import com.aptech.ticketshow.data.dtos.StatusDTO;
import com.aptech.ticketshow.services.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/status")
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @GetMapping
    public ResponseEntity<List<StatusDTO>> findAll() {
        List<StatusDTO> statusDTOs = statusService.findAll();
        return ResponseEntity.ok(statusDTOs);
    }
}