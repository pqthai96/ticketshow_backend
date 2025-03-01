package com.aptech.ticketshow.data.repositories;

import com.aptech.ticketshow.data.entities.Event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findAll(Specification<Event> filter, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.title LIKE %:searchValue% AND e.status.id = 1L")
    Page<Event> search(@Param("searchValue") String searchValue, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE (:id is null or e.id = :id)")
    Optional<Event> findById(@Param("id") Long id);

    Page<Event> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT e FROM Event e LEFT JOIN Order o ON o.event.id = e.id " +
            "GROUP BY e.id " +
            "ORDER BY COUNT(o.id) DESC")
    Page<Event> findBestSellingEvents(Pageable pageable);
}
