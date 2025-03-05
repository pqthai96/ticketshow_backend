package com.aptech.ticketshow.data.repositories;

import com.aptech.ticketshow.data.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUserId(long id);

    List<Order> findByVoucherId(long id);

    List<Order> findByEventIdAndStatusIdIn(Long eventId, List<Long> statusIds);

    List<Order> findAllByOrderDateBetween(Date startDate, Date endDate);
}