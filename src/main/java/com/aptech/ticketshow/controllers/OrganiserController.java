package com.aptech.ticketshow.controllers;

import com.aptech.ticketshow.data.dtos.OrganiserDTO;
import com.aptech.ticketshow.services.OrganiserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/organiser")
@RequiredArgsConstructor
public class OrganiserController {

    @Autowired
    private OrganiserService organizerService;

    @GetMapping
    public ResponseEntity<List<OrganiserDTO>> getOrganisers() {
        List<OrganiserDTO> organizers = organizerService.findAll();
        return ResponseEntity.ok(organizers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganiserDTO> getOrganiserById(@PathVariable Long id) {
        OrganiserDTO organizer = organizerService.findById(id);
        return ResponseEntity.ok(organizer);
    }

    @PostMapping
    public ResponseEntity<OrganiserDTO> createOrganiser(@RequestBody OrganiserDTO organizerDTO) {
        OrganiserDTO createdOrganiser = organizerService.save(organizerDTO);
        return ResponseEntity.created(URI.create("/api/organizer/" + createdOrganiser.getId())).body(createdOrganiser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganiserDTO> updateOrganiser(@PathVariable Long id, @RequestBody OrganiserDTO organizerDTO) {
        organizerDTO.setId(id);
        OrganiserDTO updatedOrganiser = organizerService.update(organizerDTO);
        return ResponseEntity.ok(updatedOrganiser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganiserById(@PathVariable Long id) {
        organizerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}