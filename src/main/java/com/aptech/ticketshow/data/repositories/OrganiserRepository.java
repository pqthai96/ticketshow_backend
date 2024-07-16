package com.aptech.ticketshow.data.repositories;

import com.aptech.ticketshow.data.entities.Organiser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganiserRepository extends JpaRepository<Organiser, Long> {
}
