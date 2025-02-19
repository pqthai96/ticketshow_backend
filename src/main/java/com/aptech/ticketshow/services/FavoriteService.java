package com.aptech.ticketshow.services;

import java.util.List;

import com.aptech.ticketshow.data.dtos.FavoriteDTO;

public interface FavoriteService {

	List<FavoriteDTO> findAll();

    List<FavoriteDTO> findByUserId(Long userId);
	
    FavoriteDTO create(FavoriteDTO favoriteDTO);

    FavoriteDTO findById(Long id);

    FavoriteDTO update(FavoriteDTO favoriteDTO);

    void delete(Long id);
}
