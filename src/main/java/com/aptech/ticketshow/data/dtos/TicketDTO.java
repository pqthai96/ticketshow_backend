package com.aptech.ticketshow.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private Long id;

    private String title;

    private String description;

    private Double price;

    private String type;

    private Integer quantity;

    private Boolean isPaused;

    private Boolean isHidden;

     private EventDTO event;

    private AdminDTO editedByAdminId;
}
