package com.aptech.ticketshow.data.repositories;

import com.aptech.ticketshow.data.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}