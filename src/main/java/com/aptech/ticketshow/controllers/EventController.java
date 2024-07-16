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

@RestController
@RequestMapping("api/event")
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

        return ResponseEntity.ok(eventService.findByID(eventDTO.getId()));
    }
}
