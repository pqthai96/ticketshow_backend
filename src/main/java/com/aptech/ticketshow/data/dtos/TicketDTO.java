package com.aptech.ticketshow.data.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class TicketDTO {
    private Long id;

    private String title;

    private String description;

    private Double price;

    private String type;

    private Integer quantity;

    private Boolean isPaused;

    private Boolean isHidden;

    private EventDTO eventDTO;

    private AdminDTO adminDTO;
}