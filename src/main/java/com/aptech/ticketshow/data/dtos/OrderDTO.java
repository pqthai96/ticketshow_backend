package com.aptech.ticketshow.data.dtos;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {

    private String id;

    private String emailReceive;

    private Date orderDate;

    private String ticketPdfPath;

    private String transactionId;

    private StatusDTO statusDTO;

    private UserDTO userDTO;

    private EventDTO eventDTO;

    private VoucherDTO voucherDTO;

    private List<OrderItemDTO> orderItemDTOs;

    private Date createdAt;
}
