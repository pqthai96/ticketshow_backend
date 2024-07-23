package com.aptech.ticketshow.controllers;

import com.aptech.ticketshow.data.dtos.EventDTO;
import com.aptech.ticketshow.services.EventService;
import com.aptech.ticketshow.services.FileStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    private static final String UPLOAD_PATH = "";

    @Autowired
    EventService eventService;

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    @CrossOrigin
    public ResponseEntity<EventDTO> createEvent(
            @RequestParam("event") String event,
            @RequestParam(value = "bannerImg", required = false) MultipartFile bannerImg,
            @RequestParam(value = "positionImg", required = false) MultipartFile positionImg) {
        ObjectMapper mapper = new ObjectMapper();
        EventDTO eventDTO;
        try {
            eventDTO = mapper.readValue(event, EventDTO.class);
            eventService.addEvent(eventDTO, bannerImg, positionImg);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
        //filesStorageService.save(bannerImg);

        return ResponseEntity.ok(eventDTO);
    }

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
