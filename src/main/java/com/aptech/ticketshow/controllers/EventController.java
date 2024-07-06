package com.aptech.ticketshow.controllers;

import com.aptech.ticketshow.data.dtos.CreateEventDTO;
import com.aptech.ticketshow.data.dtos.CreateTicketDTO;
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

//    @Autowired
//    FileStorageService filesStorageService;

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    @CrossOrigin
    public ResponseEntity<EventDTO> addEvent(@RequestParam(name = "event") String event, @RequestParam(name = "bannerImg") MultipartFile bannerImg, @RequestParam(name = "positionImg") MultipartFile positionImg) {
        ObjectMapper mapper = new ObjectMapper();
        CreateEventDTO e;
        try {
            e = mapper.readValue(event, CreateEventDTO.class);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
        for(CreateTicketDTO c:e.getTickets())
        {
            System.out.println(c.getTitle());
        }
        //filesStorageService.save(bannerImg);
//        EventDTO eventDTO=new EventDTO();
//        eventDTO.setTitle(e.getTitle());
//        eventDTO.setTitle(e.getTitle());
//        eventDTO.setTitle(e.getTitle());
//        eventDTO.setTitle(e.getTitle());
//        eventDTO.setTitle(e.getTitle());
//        eventDTO.setTitle(e.getTitle());
//        eventDTO.setTitle(e.getTitle());
        return ResponseEntity.ok(eventService.findByID(1L));
    }
}
