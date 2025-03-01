package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.*;
import com.aptech.ticketshow.data.dtos.request.CheckoutRequest;
import com.aptech.ticketshow.exception_v2.TicketExceptionV2;
import com.aptech.ticketshow.services.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TicketValidationServiceImpl implements TicketValidationService {

    private static final Logger log = LoggerFactory.getLogger(TicketValidationService.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private EventService eventService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean validateTicketAvailability(CheckoutRequest checkoutRequest) {
        // Get event information
        EventDTO eventDTO = eventService.findById(checkoutRequest.getEventId());

        // Get active and pending order statuses
        List<Long> activeStatusIds = getCompletedAndPendingStatusIds();

        // Check based on event type
        if (eventDTO.isType()) {
            // For quantity-based tickets (general admission)
            return validateQuantityBasedTickets(checkoutRequest, eventDTO);
        } else {
            // For seat-based tickets (reserved seating)
            return validateSeatBasedTickets(checkoutRequest, eventDTO, activeStatusIds);
        }
    }

    private List<Long> getCompletedAndPendingStatusIds() {
        List<Long> statusIds = new ArrayList<>();
        statusIds.add(4L); // Pending status ID
        statusIds.add(5L); // Completed status ID
        return statusIds;
    }

    private boolean validateQuantityBasedTickets(CheckoutRequest checkoutRequest, EventDTO eventDTO) {
        // Get all tickets in the cart
        List<CartDTO> cartItems = checkoutRequest.getCartDTOs();
        if (cartItems == null || cartItems.isEmpty()) {
            throw new TicketExceptionV2("No tickets selected for purchase");
        }

        // Check each ticket type in the cart
        for (CartDTO cartItem : cartItems) {
            TicketDTO ticketDTO = ticketService.findById(cartItem.getId());

            // Get current quantity of tickets of this type
            int requestedQuantity = cartItem.getQuantity();
            int totalInventory = ticketDTO.getQuantity();

            // Calculate tickets in pending orders only
            int pendingQuantity = calculatePendingTickets(ticketDTO.getId(),eventDTO.getId());

            // Check if enough tickets are available (total inventory minus pending orders)
            if (totalInventory - pendingQuantity < requestedQuantity) {
                throw new TicketExceptionV2("Not enough tickets available for: " + ticketDTO.getTitle() +
                        ". Available: " + (totalInventory - pendingQuantity) +
                        ", Requested: " + requestedQuantity);
            }
        }

        return true;
    }

    private int calculatePendingTickets(Long ticketId, Long eventId) {
        // Get all pending orders for this event
        List<OrderDTO> pendingOrders = orderService.findByEventIdAndStatusIds(eventId, List.of(4L));

        // Count tickets reserved in pending orders
        int pendingCount = 0;
        for (OrderDTO orderDTO : pendingOrders) {
            List<OrderItemDTO> orderItems = orderItemService.findByOrderId(orderDTO.getId());

            for (OrderItemDTO orderItemDTO : orderItems) {
                // Check if this order item is for the ticket we're validating
                if (orderItemDTO.getTicketDTO() != null &&
                        orderItemDTO.getTicketDTO().getId().equals(ticketId)) {
                    pendingCount += orderItemDTO.getQuantity();
                }
            }
        }

        return pendingCount;
    }

    private boolean validateSeatBasedTickets(CheckoutRequest checkoutRequest, EventDTO eventDTO, List<Long> activeStatusIds) {
        List<String> requestedSeats = checkoutRequest.getSeats();
        if (requestedSeats == null || requestedSeats.isEmpty()) {
            throw new TicketExceptionV2("No seats selected for purchase");
        }

        try {
            // Parse booked seats from JSON string
            String[] bookedSeatsArray = objectMapper.readValue(eventDTO.getBookedSeats(), String[].class);
            List<String> bookedSeats = new ArrayList<>(Arrays.asList(bookedSeatsArray));

            // Also get seats from active/pending orders
            List<String> pendingSeats = getPendingSeats(eventDTO.getId(), activeStatusIds);

            // Combine both lists to get all unavailable seats
            List<String> unavailableSeats = new ArrayList<>(bookedSeats);
            unavailableSeats.addAll(pendingSeats);

            // Check if any requested seat is already booked or pending
            for (String seat : requestedSeats) {
                if (unavailableSeats.contains(seat)) {
                    throw new TicketExceptionV2("Seat " + seat + " is no longer available");
                }
            }

            return true;
        } catch (JsonProcessingException e) {
            log.error("Error parsing booked seats", e);
            throw new TicketExceptionV2("Error validating seat availability");
        }
    }

    private List<String> getPendingSeats(Long eventId, List<Long> statusIds) {
        // Get all active/pending orders for this event
        List<OrderDTO> activeOrders = orderService.findByEventIdAndStatusIds(eventId, statusIds);

        // Collect all seats in pending orders
        List<String> pendingSeats = new ArrayList<>();
        for (OrderDTO orderDTO : activeOrders) {
            for (OrderItemDTO orderItemDTO : orderItemService.findByOrderId(orderDTO.getId())) {
                if (orderItemDTO.getSeatValue() != null && !orderItemDTO.getSeatValue().isEmpty()) {
                    pendingSeats.add(orderItemDTO.getSeatValue());
                }
            }
        }

        return pendingSeats;
    }
}