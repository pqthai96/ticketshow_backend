package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.CartDTO;
import com.aptech.ticketshow.data.dtos.OrderDTO;
import com.aptech.ticketshow.data.dtos.OrderItemDTO;
import com.aptech.ticketshow.data.dtos.request.CheckoutRequest;
import com.aptech.ticketshow.data.entities.Order;
import com.aptech.ticketshow.data.entities.OrderItem;
import com.aptech.ticketshow.data.entities.Ticket;
import com.aptech.ticketshow.data.mappers.OrderItemMapper;
import com.aptech.ticketshow.data.mappers.OrderMapper;
import com.aptech.ticketshow.data.repositories.*;
import com.aptech.ticketshow.services.OrderItemService;
import com.aptech.ticketshow.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public List<OrderDTO> findAll() {
        List<OrderDTO> orderDTOs = orderRepository.findAll().stream().map(r -> orderMapper.toDTO(r)).collect(Collectors.toList());

        for (OrderDTO orderDTO : orderDTOs) {
            List<OrderItemDTO> orderItemDTOs = orderItemRepository.findByOrderId(orderDTO.getId()).stream().map(r -> orderItemMapper.toDTO(r)).collect(Collectors.toList());
            orderDTO.setOrderItemDTOs(orderItemDTOs);
        }

        return orderDTOs;
    }

    @Override
    public OrderDTO findById(String id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isPresent()) {
            OrderDTO orderDTO = orderMapper.toDTO(optionalOrder.get());
            orderDTO.setOrderItemDTOs(orderItemService.findByOrderId(orderDTO.getId()));

            return orderDTO;
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }

    @Override
    public List<OrderDTO> findByUserId(Long userId) {

        List<OrderDTO> orderDTOs = orderRepository.findByUserId(userId).stream().map(r -> orderMapper.toDTO(r)).collect(Collectors.toList());

        for (OrderDTO orderDTO : orderDTOs) {
            orderDTO.setOrderItemDTOs(orderItemService.findByOrderId(orderDTO.getId()));
        }

        return orderDTOs;
    }

    @Override
    public OrderDTO create(OrderDTO orderDTO, CheckoutRequest checkoutRequest) {

        Order order = orderMapper.toEntity(orderDTO);

        order.setStatus(statusRepository.findById(4L).orElseThrow());
        order.setOrderDate(new Date());

        Order savedOrder = orderRepository.save(order);

        if (savedOrder.getEvent().isType()) {
            for (CartDTO cartDTO : checkoutRequest.getCartDTOs()) {
                Ticket ticket = ticketRepository.findById(cartDTO.getId()).orElseThrow();

                OrderItem orderItem = new OrderItem();
                orderItem.setTicket(ticket);
                orderItem.setQuantity(cartDTO.getQuantity());
                orderItem.setOrder(savedOrder);
                orderItemRepository.save(orderItem);
            }
        } else {
            for (String seat : checkoutRequest.getSeats()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setSeatValue(seat);
                orderItem.setQuantity(1);
                orderItem.setOrder(savedOrder);
                orderItemRepository.save(orderItem);
            }
        }

        return orderMapper.toDTO(savedOrder);
    }

    @Override
    public OrderDTO update(OrderDTO orderDTO) {
        return orderMapper.toDTO(orderRepository.save(orderMapper.toEntity(orderDTO)));
    }

    @Override
    public List<OrderDTO> findByVoucherId(Long voucherId) {
        List<OrderDTO> orderDTOs = orderRepository.findByVoucherId(voucherId).stream().map(r -> orderMapper.toDTO(r)).collect(Collectors.toList());

        for (OrderDTO orderDTO : orderDTOs) {
            orderDTO.setOrderItemDTOs(orderItemService.findByOrderId(orderDTO.getId()));
        }

        return orderDTOs;
    }

    @Override
    public String generateOrderCode() {
        Calendar calendar = Calendar.getInstance();
        String dateStr = String.format("%04d%02d%02d",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));

        String allowedChars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

        StringBuilder randomPart = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(allowedChars.length());
            randomPart.append(allowedChars.charAt(index));
        }

        return "TS-" + dateStr + "-" + randomPart.toString();
    }

    @Override
    public List<OrderDTO> findByEventIdAndStatusIds(Long eventId, List<Long> statusIds) {
        List<Order> orders = orderRepository.findByEventIdAndStatusIdIn(eventId, statusIds);
        return orders.stream()
                .map(order -> orderMapper.toDTO(order))
                .collect(Collectors.toList());
    }
}
