package com.aptech.ticketshow.data.repositories;

import com.aptech.ticketshow.data.entities.Event;
import com.aptech.ticketshow.data.entities.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRespository extends JpaRepository<Event, Long> {
}
