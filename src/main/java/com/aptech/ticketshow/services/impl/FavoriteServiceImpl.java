package com.aptech.ticketshow.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.aptech.ticketshow.data.entities.Event;
import com.aptech.ticketshow.data.entities.User;
import com.aptech.ticketshow.data.mappers.EventMapper;
import com.aptech.ticketshow.data.mappers.UserMapper;
import com.aptech.ticketshow.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;

import com.aptech.ticketshow.data.dtos.FavoriteDTO;
import com.aptech.ticketshow.data.entities.Favorite;
import com.aptech.ticketshow.data.mappers.FavoriteMapper;
import com.aptech.ticketshow.data.repositories.FavoriteRepository;
import com.aptech.ticketshow.services.FavoriteService;
import com.aptech.ticketshow.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl implements FavoriteService {

	@Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EventMapper eventMapper;

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
        Event event = eventMapper.toEntity(favoriteDTO.getEventDTO());
        User user = userMapper.toEntity(favoriteDTO.getUserDTO()); // Chuyển đổi UserDTO thành User

        favorite.setEvent(event);
        favorite.setUser(user);
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
    public FavoriteDTO update(Long id, FavoriteDTO favoriteDTO) {
        return favoriteRepository.findById(id)
                .map(existingFavorite -> {
                    // Lấy event và user trực tiếp từ favoriteDTO bằng ID
                    Event event = eventMapper.toEntity(favoriteDTO.getEventDTO());
                    User user = userMapper.toEntity(favoriteDTO.getUserDTO());

                    existingFavorite.setEvent(event);
                    existingFavorite.setUser(user);
                    return favoriteMapper.toDTO(favoriteRepository.save(existingFavorite));
                })
                .orElse(null);
    }


    @Override
	public void delete(Long id) {
		favoriteRepository.deleteById(id);
		
	}
}
