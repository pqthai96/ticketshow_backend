package com.aptech.ticketshow.controllers;

import com.aptech.ticketshow.common.config.JwtUtil;
import com.aptech.ticketshow.data.dtos.OrderDTO;
import com.aptech.ticketshow.data.dtos.UserDTO;
import com.aptech.ticketshow.services.OrderService;
import com.aptech.ticketshow.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable String id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping("/orders-by-user")
    public ResponseEntity<?> findAllOrderByUser(@RequestHeader("Authorization") String token) {
        UserDTO userDTO = jwtUtil.extractUser(token);

        List<OrderDTO> orderDTOs = orderService.findByUserId(userDTO.getId());

        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/download/{orderId}")
    public ResponseEntity<?> downloadInvoice(@PathVariable String orderId) {
        try {
            OrderDTO orderDTO = orderService.findById(orderId);

            if (orderDTO == null) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new FileSystemResource("uploads" + orderDTO.getTicketPdfPath());
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Invoice-" + orderDTO.getTransactionId() + ".pdf\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/update-status")
    public ResponseEntity<?> updateStatus(@RequestParam("orderId") String orderId, @RequestParam("statusId") String statusId) {
        OrderDTO updateStatusOrderDTO = orderService.findById(orderId);
        updateStatusOrderDTO.setStatusDTO(statusService.findById(Long.valueOf(statusId)));
        return ResponseEntity.ok(orderService.update(updateStatusOrderDTO));
    }
}
