package com.aptech.ticketshow.controllers;

import com.aptech.ticketshow.common.config.JwtUtil;
import com.aptech.ticketshow.data.dtos.*;
import com.aptech.ticketshow.data.dtos.request.ModifyEventRequest;
import com.aptech.ticketshow.services.EventService;
import com.aptech.ticketshow.services.FavoriteService;
import com.aptech.ticketshow.services.ImageUploadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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
    private ImageUploadService imageUploadService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int no, @RequestParam(defaultValue = "12") int limit) {
        return ResponseEntity.ok(eventService.findAll(no, limit));
    }

    @GetMapping("/test")
    public ResponseEntity<?> findAllTest() {
        return ResponseEntity.ok("test ok");
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

    @GetMapping("/category")
    public ResponseEntity<PaginationDTO> findAllByCategory(@RequestParam int no,
                                                           @RequestParam int limit,
                                                           @RequestParam Long categoryId) {
        return ResponseEntity.ok(eventService.findAllByCategory(no, limit, categoryId));
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

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createEvent(HttpServletRequest httpServletRequest,
                                         @RequestPart(value = "bannerImage", required = true) MultipartFile bannerImage,
                                         @RequestPart(value = "positionImage", required = false) MultipartFile positionImage) {
        try {
            Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();

            ModifyEventRequest modifyEventRequest = new ModifyEventRequest();

            if (parameterMap.containsKey("title")) modifyEventRequest.setTitle(parameterMap.get("title")[0]);
            if (parameterMap.containsKey("statusId")) modifyEventRequest.setStatusId(Long.valueOf(parameterMap.get("statusId")[0]));
            if (parameterMap.containsKey("venueName")) modifyEventRequest.setVenueName(parameterMap.get("venueName")[0]);
            if (parameterMap.containsKey("type")) modifyEventRequest.setType(Boolean.valueOf(parameterMap.get("type")[0]));
            if (parameterMap.containsKey("locationAddress")) modifyEventRequest.setLocationAddress(parameterMap.get("locationAddress")[0]);
            if (parameterMap.containsKey("locationProvince")) modifyEventRequest.setLocationProvince(parameterMap.get("locationProvince")[0]);
            if (parameterMap.containsKey("locationDistrict")) modifyEventRequest.setLocationDistrict(parameterMap.get("locationDistrict")[0]);
            if (parameterMap.containsKey("locationWard")) modifyEventRequest.setLocationWard(parameterMap.get("locationWard")[0]);
            if (parameterMap.containsKey("startedAt")) modifyEventRequest.setStartedAt(Date.from(LocalDate.parse(parameterMap.get("startedAt")[0]).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            if (parameterMap.containsKey("endedAt")) modifyEventRequest.setEndedAt(Date.from(LocalDate.parse(parameterMap.get("endedAt")[0]).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            if (parameterMap.containsKey("content")) modifyEventRequest.setContent(parameterMap.get("content")[0]);
            if (parameterMap.containsKey("categoryId")) modifyEventRequest.setCategoryId(Long.valueOf(parameterMap.get("categoryId")[0]));
            if (parameterMap.containsKey("organiserId")) modifyEventRequest.setOrganiserId(Long.valueOf(parameterMap.get("organiserId")[0]));

            // Parse seatPrice (BigDecimal)
            if (parameterMap.containsKey("seatPrice") && parameterMap.get("seatPrice")[0] != null && !parameterMap.get("seatPrice")[0].isEmpty()) {
                modifyEventRequest.setSeatPrice(Double.parseDouble(parameterMap.get("seatPrice")[0]));
            }

            // Parse bookedSeats (String)
            if (parameterMap.containsKey("bookedSeats")) {
                modifyEventRequest.setBookedSeats("[]");
            }

            // Parse tickets (JSON array)
            if (parameterMap.containsKey("tickets")) {
                String ticketsJson = parameterMap.get("tickets")[0];
                if (ticketsJson != null && !ticketsJson.isEmpty()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    TicketDTO[] ticketArray = objectMapper.readValue(ticketsJson, TicketDTO[].class);
                    modifyEventRequest.setTicketDTOs(Arrays.asList(ticketArray));
                }
            }

            EventDTO createdEventDTO = eventService.create(modifyEventRequest, bannerImage, positionImage);

            return ResponseEntity.ok(createdEventDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request: " + e.getMessage());
        }
    }

    @PostMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editEvent(HttpServletRequest httpServletRequest,
                                         @RequestPart(value = "bannerImage", required = false) MultipartFile bannerImage,
                                         @RequestPart(value = "positionImage", required = false) MultipartFile positionImage) {
        try {
            Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();

            ModifyEventRequest modifyEventRequest = new ModifyEventRequest();

            if (parameterMap.containsKey("id")) modifyEventRequest.setId(Long.valueOf(parameterMap.get("id")[0]));
            if (parameterMap.containsKey("title")) modifyEventRequest.setTitle(parameterMap.get("title")[0]);
            if (parameterMap.containsKey("statusId")) modifyEventRequest.setStatusId(Long.valueOf(parameterMap.get("statusId")[0]));
            if (parameterMap.containsKey("venueName")) modifyEventRequest.setVenueName(parameterMap.get("venueName")[0]);
            if (parameterMap.containsKey("type")) modifyEventRequest.setType(Boolean.valueOf(parameterMap.get("type")[0]));
            if (parameterMap.containsKey("locationAddress")) modifyEventRequest.setLocationAddress(parameterMap.get("locationAddress")[0]);
            if (parameterMap.containsKey("locationProvince")) modifyEventRequest.setLocationProvince(parameterMap.get("locationProvince")[0]);
            if (parameterMap.containsKey("locationDistrict")) modifyEventRequest.setLocationDistrict(parameterMap.get("locationDistrict")[0]);
            if (parameterMap.containsKey("locationWard")) modifyEventRequest.setLocationWard(parameterMap.get("locationWard")[0]);
            if (parameterMap.containsKey("startedAt")) modifyEventRequest.setStartedAt(Date.from(LocalDate.parse(parameterMap.get("startedAt")[0]).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            if (parameterMap.containsKey("endedAt")) modifyEventRequest.setEndedAt(Date.from(LocalDate.parse(parameterMap.get("endedAt")[0]).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            if (parameterMap.containsKey("content")) modifyEventRequest.setContent(parameterMap.get("content")[0]);
            if (parameterMap.containsKey("categoryId")) modifyEventRequest.setCategoryId(Long.valueOf(parameterMap.get("categoryId")[0]));
            if (parameterMap.containsKey("organiserId")) modifyEventRequest.setOrganiserId(Long.valueOf(parameterMap.get("organiserId")[0]));

            // Parse seatPrice (BigDecimal)
            if (parameterMap.containsKey("seatPrice") && parameterMap.get("seatPrice")[0] != null && !parameterMap.get("seatPrice")[0].isEmpty()) {
                modifyEventRequest.setSeatPrice(Double.parseDouble(parameterMap.get("seatPrice")[0]));
            }

            // Parse tickets (JSON array)
            if (parameterMap.containsKey("tickets")) {
                String ticketsJson = parameterMap.get("tickets")[0];
                if (ticketsJson != null && !ticketsJson.isEmpty()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    TicketDTO[] ticketArray = objectMapper.readValue(ticketsJson, TicketDTO[].class);
                    modifyEventRequest.setTicketDTOs(Arrays.asList(ticketArray));
                }
            }

            EventDTO editedEventDTO = eventService.edit(modifyEventRequest, bannerImage, positionImage);

            return ResponseEntity.ok(editedEventDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request: " + e.getMessage());
        }
    }
}
