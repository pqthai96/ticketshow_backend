package com.aptech.ticketshow.data.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class OrderDTO {

    private Long id;

    private String emailReceive;

    private String ticketPdfPath;

    private Integer transactionId;

    private String businessName;

    private Long businessTax;

    private String businessAddress;

    private UserDTO userDTO;

    private EventDTO eventDTO;

    private VoucherDTO voucherDTO;
}
