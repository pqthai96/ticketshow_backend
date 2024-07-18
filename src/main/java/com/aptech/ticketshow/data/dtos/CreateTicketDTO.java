package com.aptech.ticketshow.data.dtos;

import lombok.Data;

@Data
public class CreateTicketDTO {
    private String title;
    private int quantity;
    private Double price;
    private String description;
}
