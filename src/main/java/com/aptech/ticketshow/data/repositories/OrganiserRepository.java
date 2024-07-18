package com.aptech.ticketshow.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aptech.ticketshow.data.entities.Organiser;

@Repository
public interface OrganiserRepository extends JpaRepository<Organiser, Long> {
}

