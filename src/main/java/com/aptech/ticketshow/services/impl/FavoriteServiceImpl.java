package com.aptech.ticketshow.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.ticketshow.data.dtos.FavoriteDTO;
import com.aptech.ticketshow.data.entities.Event;
import com.aptech.ticketshow.data.entities.Favorite;
import com.aptech.ticketshow.data.entities.User;
import com.aptech.ticketshow.data.mappers.FavoriteMapper;
import com.aptech.ticketshow.data.repositories.EventRepository;
import com.aptech.ticketshow.data.repositories.FavoriteRepository;
import com.aptech.ticketshow.data.repositories.UserRepository;
import com.aptech.ticketshow.services.EventService;
import com.aptech.ticketshow.services.FavoriteService;
import com.aptech.ticketshow.services.UserService;

@Service
public class FavoriteServiceImpl implements FavoriteService {

	@Autowired
    private FavoriteRepository favoriteRepository;
	
	@Autowired
    private EventRepository eventRepository;
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private FavoriteMapper favoriteMapper;

    
    @Override
    public List<FavoriteDTO> findAll() {
        return favoriteRepository.findAll().stream().map(r -> favoriteMapper.toDTO(r)).collect(Collectors.toList());
    }

	@Override
	public FavoriteDTO create(FavoriteDTO favoriteDTO) {
		Favorite favorite = favoriteMapper.toEntity(favoriteDTO);

	    Optional<Event> optionalEvent = eventRepository.findById(favoriteDTO.getEventDTO().getId());
	    Optional<User> optionalUser = userRepository.findById(favoriteDTO.getUserDTO().getId());

	    if (optionalEvent.isPresent() && optionalUser.isPresent()) {
	        Event event = optionalEvent.get();
	        User user = optionalUser.get();
	        favorite.setEvent(event);
	        favorite.setUser(user);
	    } else {
	    }

	    favorite = favoriteRepository.save(favorite);
	    return favoriteMapper.toDTO(favorite);
	}

	@Override
	public FavoriteDTO findById(Long id) {
		Optional<Favorite> optionalFavorite = favoriteRepository.findById(id);
        if (optionalFavorite.isPresent()) {
            return favoriteMapper.toDTO(optionalFavorite.get());
        }
        return null;
	}

	@Override
	public FavoriteDTO update(FavoriteDTO favoriteDTO) {
		long id = favoriteDTO.getId();
		Optional<Favorite> optionalFavorite = favoriteRepository.findById(id);
        if (optionalFavorite.isPresent()) {
        	optionalFavorite.get().setEvent(eventRepository.findById(favoriteDTO.getEventDTO().getId()).orElse(null));
        	optionalFavorite.get().setUser(userRepository.findById(favoriteDTO.getUserDTO().getId()).orElse(null));
            Favorite existingFavorite = optionalFavorite.get();
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
