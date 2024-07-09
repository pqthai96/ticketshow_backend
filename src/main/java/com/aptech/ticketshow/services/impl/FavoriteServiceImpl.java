package com.aptech.ticketshow.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.aptech.ticketshow.data.repositories.EventRepository;
import com.aptech.ticketshow.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.ticketshow.data.dtos.FavoriteDTO;
import com.aptech.ticketshow.data.entities.Favorite;
import com.aptech.ticketshow.data.mappers.FavoriteMapper;
import com.aptech.ticketshow.data.repositories.FavoriteRepository;
import com.aptech.ticketshow.services.EventService;
import com.aptech.ticketshow.services.FavoriteService;
import com.aptech.ticketshow.services.UserService;

@Service
public class FavoriteServiceImpl implements FavoriteService {

	@Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public List<FavoriteDTO> findAll() {
        return favoriteRepository.findAll().stream().map(r -> favoriteMapper.toDTO(r)).collect(Collectors.toList());
    }

	@Override
	public FavoriteDTO create(FavoriteDTO favoriteDTO) {
		Favorite favorite = favoriteMapper.toEntity(favoriteDTO);
      
		favorite.setEvent(eventRepository.findById(favoriteDTO.getEventDTO().getId()).orElse(null));
		favorite.setUser(userRepository.findById(favoriteDTO.getUserDTO().getId()).orElse(null));
        favorite = favoriteRepository.save(favorite);
        return favoriteMapper.toDTO(favorite);
	}

    @Override
	public FavoriteDTO findById(Long id) {
		Optional<Favorite> optionalFavorite = favoriteRepository.findById(id);
        return optionalFavorite.map(favorite -> favoriteMapper.toDTO(favorite)).orElse(null);
    }

	@Override
	public FavoriteDTO update(FavoriteDTO favoriteDTO) {
		Optional<Favorite> optionalFavorite = favoriteRepository.findById(favoriteDTO.getId());
        if (optionalFavorite.isPresent()) {
            Favorite existingFavorite = optionalFavorite.get();
             existingFavorite.setEvent(eventRepository.findById(favoriteDTO.getEventDTO().getId()).orElse(null));
             existingFavorite.setUser(userRepository.findById(favoriteDTO.getUserDTO().getId()).orElse(null));

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
