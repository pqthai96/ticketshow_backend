package com.aptech.ticketshow.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.ticketshow.data.dtos.FavoriteDTO;
import com.aptech.ticketshow.data.entities.Favorite;
import com.aptech.ticketshow.data.mappers.FavoriteMapper;
import com.aptech.ticketshow.data.repositories.FavoriteRepository;
import com.aptech.ticketshow.services.FavoriteService;
import com.aptech.ticketshow.services.UserService;

@Service
public class FavoriteServiceImpl implements FavoriteService {

	@Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;
    
    @Override
    public List<FavoriteDTO> findAll() {
        return favoriteRepository.findAll().stream().map(r -> favoriteMapper.toDTO(r)).collect(Collectors.toList());
    }

	@Override
	public FavoriteDTO create(FavoriteDTO favoriteDTO) {
		Favorite favorite = favoriteMapper.toEntity(favoriteDTO);
      
		favorite.setEvent(eventService.getById(favoriteDTO.getEventDTO().getId()));
		favorite.setUser(userService.getById(favoriteDTO.getUserDTO().getId()));
        favorite = favoriteRepository.save(favorite);
        return favoriteMapper.toDTO(favorite);
	}

	@Override
	public FavoriteDTO getById(Long id) {
		Optional<Favorite> optionalFavorite = favoriteRepository.findById(id);
        if (optionalFavorite.isPresent()) {
            return favoriteMapper.toDTO(optionalFavorite.get());
        }
        return null;
	}

	@Override
	public FavoriteDTO update(Long id, FavoriteDTO favoriteDTO) {
		Optional<Favorite> optionalFavorite = favoriteRepository.findById(id);
        if (optionalFavorite.isPresent()) {
            Favorite existingFavorite = optionalFavorite.get();
             existingFavorite.setEvent(eventService.getById(favoriteDTO.getEventDTO().getId()));
             existingFavorite.setUser(userService.getById(favoriteDTO.getUserDTO().getId()));

            existingFavorite = favoriteRepository.save(existingFavorite);
            return favoriteMapper.toDTO(existingFavorite);
        }
        return null; 
	}

	@Override
	public void delete(Long id) {
		favoriteRepository.deleteById(id);
		
	}
}
