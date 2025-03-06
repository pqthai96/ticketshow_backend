package com.aptech.ticketshow.data.repositories;

import com.aptech.ticketshow.data.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(String orderId);

    @Query("SELECT oi FROM OrderItem oi JOIN oi.order o JOIN oi.ticket t " +
            "WHERE t.id = :ticketId AND o.status.id = 5L")
    List<OrderItem> findByTicketIdAndOrderStatusCompleted(@Param("ticketId") Long ticketId);

    List<OrderItem> findByOrderIdIn(List<String> orderIds);
}