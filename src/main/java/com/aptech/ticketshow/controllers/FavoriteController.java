package com.aptech.ticketshow.controllers;

import java.util.List;

import com.aptech.ticketshow.common.config.JwtUtil;
import com.aptech.ticketshow.data.dtos.UserDTO;
import com.aptech.ticketshow.data.dtos.UserProfileDTO;
import com.aptech.ticketshow.services.EventService;
import com.aptech.ticketshow.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aptech.ticketshow.data.dtos.FavoriteDTO;
import com.aptech.ticketshow.services.FavoriteService;

@RestController
@RequestMapping("api/favorite")
public class FavoriteController {

	@Autowired
	private FavoriteService favoriteService;

    @Autowired
    private EventService eventService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/is-like/{eventId}")
    public ResponseEntity<Boolean> isLike(@PathVariable("eventId") Long eventId, @RequestHeader("Authorization") String token) {

        UserDTO userDTO = jwtUtil.extractUser(token);

        List<FavoriteDTO> favoriteDTOs = favoriteService.findByUserId(userDTO.getId());
        for (FavoriteDTO favoriteDTO : favoriteDTOs) {
            if (favoriteDTO.getEventDTO().getId().equals(eventId)) {
                return ResponseEntity.ok(true);
            }
        }

        return ResponseEntity.ok(false);
    }

    @PostMapping("/{eventId}")
    public ResponseEntity<?> create(@PathVariable("eventId") Long eventId, @RequestHeader("Authorization") String token) {

        UserDTO userDTO = jwtUtil.extractUser(token);

        FavoriteDTO favoriteDTO = new FavoriteDTO();
        favoriteDTO.setEventDTO(eventService.findById(eventId));
        favoriteDTO.setUserDTO(userDTO);

        favoriteService.create(favoriteDTO);

        return ResponseEntity.ok("Created!");
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Boolean> delete(@PathVariable("eventId") Long eventId, @RequestHeader("Authorization") String token) {
        UserDTO userDTO = jwtUtil.extractUser(token);

        List<FavoriteDTO> favoriteDTOs = favoriteService.findByUserId(userDTO.getId());
        for (FavoriteDTO favoriteDTO : favoriteDTOs) {
            if (favoriteDTO.getEventDTO().getId().equals(eventId)) {
                favoriteService.delete(favoriteDTO.getId());
                return ResponseEntity.ok(true);
            }
        }
        return ResponseEntity.ok(false);
    }
}
