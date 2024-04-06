package com.aptech.ticketshow.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.ticketshow.data.dtos.FavoriteDTO;
import com.aptech.ticketshow.services.FavoriteService;

@RestController
@RequestMapping("api/favorite")
public class FavoriteController {

	@Autowired
	private FavoriteService favoriteService;
	@GetMapping
    public ResponseEntity<List<FavoriteDTO>> getAllFavorites() {
        List<FavoriteDTO> favorites = favoriteService.findAll();
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoriteDTO> getFavoriteById(@PathVariable("id") Long id) {
    	FavoriteDTO favoriteDTO = favoriteService.getById(id);
        if (favoriteDTO != null) {
            return new ResponseEntity<>(favoriteDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<FavoriteDTO> createFavorite(@RequestBody FavoriteDTO favoriteDTO) {
    	FavoriteDTO createdFavorite = favoriteService.create(favoriteDTO);
        return new ResponseEntity<>(createdFavorite, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FavoriteDTO> updateFavorite(@PathVariable("id") Long id, @RequestBody FavoriteDTO favoriteDTO) {
    	FavoriteDTO updatedFavorite = favoriteService.update(id, favoriteDTO);
        if (updatedFavorite != null) {
            return new ResponseEntity<>(updatedFavorite, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable("id") Long id) {
    	favoriteService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
