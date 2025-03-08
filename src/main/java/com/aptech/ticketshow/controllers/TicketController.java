package com.aptech.ticketshow.controllers;

import com.aptech.ticketshow.data.dtos.AdminDTO;
import com.aptech.ticketshow.data.dtos.TicketDTO;
import com.aptech.ticketshow.services.AdminService;
import com.aptech.ticketshow.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/ticket")
@RequiredArgsConstructor
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketDTO>> findAll() {
        List<TicketDTO> tickets = ticketService.findAll();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> findById(@PathVariable Long id) {
        TicketDTO ticket = ticketService.findById(id);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<?> findByEventId(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.findByEventId(id));
    }

    @GetMapping("/booked-count/{id}")
    public ResponseEntity<?> getgetTicketsBookedCount(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketsBookedCount(id));
    }

    @GetMapping("/verify/{orderItemId}")
    public ResponseEntity<?> verifyTicket(@PathVariable Long orderItemId) {
        return ResponseEntity.ok(ticketService.verifyTicket(orderItemId));
    }
}
