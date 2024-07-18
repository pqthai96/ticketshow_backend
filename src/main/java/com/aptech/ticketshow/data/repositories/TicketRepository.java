package com.aptech.ticketshow.data.repositories;

import com.aptech.ticketshow.data.entities.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByEventId(long eventId);
}
