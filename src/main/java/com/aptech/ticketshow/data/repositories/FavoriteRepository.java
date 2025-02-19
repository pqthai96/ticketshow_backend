package com.aptech.ticketshow.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aptech.ticketshow.data.entities.Favorite;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long>{

    List<Favorite> findByUserId(Long userId);
}
