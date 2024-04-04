package com.aptech.ticketshow.data.dtos;

import java.util.Date;
import lombok.Data;

@Data
public class DiscountDTO {

   private Long id;

   private Date createdAt;

   private Date updatedAt;
	
   private String description;

   private Double value;

   private Date startedAt;

   private Date endedAt;

   private TicketDTO ticketDTO;
}