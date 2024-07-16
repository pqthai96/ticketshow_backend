package com.aptech.ticketshow.controllers;

import com.aptech.ticketshow.data.dtos.EventDTO;
import com.aptech.ticketshow.data.dtos.EventFilterDTO;
import com.aptech.ticketshow.data.dtos.PaginationDTO;
import com.aptech.ticketshow.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/events")
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int no, @RequestParam(defaultValue = "12") int limit) {
        return ResponseEntity.ok(eventService.findAll(no, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> findById(@PathVariable("id") Long id) {
        EventDTO eventDTO = eventService.findById(id);
        if (eventDTO != null) {
            return new ResponseEntity<>(eventDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<PaginationDTO> filter(@RequestParam int no,
                                                @RequestParam int limit,
                                                @RequestBody EventFilterDTO eventFilterDTO) {
        return ResponseEntity.ok(eventService.filter(no, limit, eventFilterDTO));
    }

    @CrossOrigin
    @GetMapping("/search")
    public ResponseEntity<PaginationDTO> search(@RequestParam int no, @RequestParam int limit, @RequestParam(value = "searchValue") String searchValue) {
        return ResponseEntity.ok(eventService.search(no, limit, searchValue));
    }

    @PostMapping("/bookedSeat")
    public ResponseEntity<EventDTO> bookedSeat(@RequestBody EventDTO eventDTO) {
        EventDTO updatedEvent = eventService.bookedSeat(eventDTO);
        return ResponseEntity.ok(updatedEvent);
    }
}