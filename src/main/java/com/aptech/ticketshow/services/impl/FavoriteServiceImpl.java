package com.aptech.ticketshow.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.ticketshow.data.dtos.FavoriteDTO;
import com.aptech.ticketshow.data.mappers.FavoriteMapper;
import com.aptech.ticketshow.data.repositories.FavoriteRepository;
import com.aptech.ticketshow.services.FavoriteService;

@Service
public class FavoriteServiceImpl implements FavoriteService {

	@Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Override
    public List<FavoriteDTO> findAll() {
        return favoriteRepository.findAll().stream().map(r -> favoriteMapper.toDTO(r)).collect(Collectors.toList());
    }
}
