package com.aptech.ticketshow.services;

import java.util.List;

import com.aptech.ticketshow.data.dtos.FavoriteDTO;

public interface FavoriteService {

	List<FavoriteDTO> findAll();
	
    FavoriteDTO create(FavoriteDTO favoriteDTO);

    FavoriteDTO getById(Long id);

    FavoriteDTO update(FavoriteDTO favoriteDTO);

    void delete(Long id);
}
