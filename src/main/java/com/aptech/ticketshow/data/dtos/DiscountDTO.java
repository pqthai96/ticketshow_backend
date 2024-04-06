package com.aptech.ticketshow.data.dtos;

import java.util.Date;

import com.aptech.ticketshow.data.dtos.TicketDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDTO {
	
	private Long id;
	
    private String description;

    private Double value;

    private Date startedAt;

    private Date endedAt;

    private TicketDTO ticketDTO;
}
