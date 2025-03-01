package com.aptech.ticketshow.controllers;

import com.aptech.ticketshow.common.config.JwtUtil;
import com.aptech.ticketshow.data.dtos.*;
import com.aptech.ticketshow.services.EventService;
import com.aptech.ticketshow.services.FavoriteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/events")
public class EventController {
    private static final String UPLOAD_PATH = "";

    @Autowired
    private EventService eventService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int no, @RequestParam(defaultValue = "12") int limit) {
        return ResponseEntity.ok(eventService.findAll(no, limit));
    }

    @GetMapping("/active")
    public ResponseEntity<?> findAllActive(@RequestParam(defaultValue = "0") int no, @RequestParam(defaultValue = "12") int limit) {
        return ResponseEntity.ok(eventService.findAllByStatus(no, limit, 1L));
    }

    @GetMapping("/ended")
    public ResponseEntity<?> findAllEnded(@RequestParam(defaultValue = "0") int no, @RequestParam(defaultValue = "12") int limit) {
        return ResponseEntity.ok(eventService.findAllByStatus(no, limit, 2L));
    }

    @GetMapping("/events-by-favorite")
    public ResponseEntity<?> findAllEventByFavorite(@RequestHeader("Authorization") String token) {
        UserDTO userDTO = jwtUtil.extractUser(token);

        List<FavoriteDTO> favoriteDTOs = favoriteService.findByUserId(userDTO.getId());
        List<EventDTO> eventDTOs = new ArrayList<>();

        for (FavoriteDTO favoriteDTO : favoriteDTOs) {
            eventDTOs.add(eventService.findById(favoriteDTO.getEventDTO().getId()));
        }
        return ResponseEntity.ok(eventDTOs);
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
    public ResponseEntity<PaginationDTO> search(@RequestParam(required = false, defaultValue = "0") int no, @RequestParam(required = false, defaultValue = "2147483647") int limit, @RequestParam(value = "searchValue") String searchValue) {
        return ResponseEntity.ok(eventService.search(no, limit, searchValue));
    }

    @PostMapping("/bookedSeat")
    public ResponseEntity<EventDTO> bookedSeat(@RequestBody EventDTO eventDTO) {
        EventDTO updatedEvent = eventService.bookedSeat(eventDTO);
        return ResponseEntity.ok(updatedEvent);
    }

    @GetMapping("/recent")
    public ResponseEntity<PaginationDTO> findRecentEvents(
            @RequestParam(defaultValue = "0") int no,
            @RequestParam(defaultValue = "3") int limit) {
        return ResponseEntity.ok(eventService.findRecentEvents(no, limit));
    }

    @GetMapping("/best-selling")
    public ResponseEntity<PaginationDTO> findBestSellingEvents(
            @RequestParam(defaultValue = "0") int no,
            @RequestParam(defaultValue = "6") int limit) {
        return ResponseEntity.ok(eventService.findBestSellingEvents(no, limit));
    }

    @PostMapping("/upload-banner")
    public ResponseEntity<?> uploadBanner(@RequestParam("file") MultipartFile bannerImage) {
        return null;
    }
}
