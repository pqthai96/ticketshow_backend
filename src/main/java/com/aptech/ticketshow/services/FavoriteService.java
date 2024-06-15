package com.aptech.ticketshow.services;

import java.util.List;

import com.aptech.ticketshow.data.dtos.FavoriteDTO;

public interface FavoriteService {

	List<FavoriteDTO> findAll();
	
    FavoriteDTO create(FavoriteDTO favoriteDTO);

    FavoriteDTO findById(Long id);

    FavoriteDTO update(Long id, FavoriteDTO favoriteDTO);

    void delete(Long id);
}
