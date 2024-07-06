package com.aptech.ticketshow.data.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class CreateEventDTO {

    private String title;

    private String venueName;

    private String locationAddress;

    private String locationProvince;

    private String locationDistrict;

    private String locationWard;

    private Date startedAt;

    private Date endedAt;

    private Date onSaleAt;

    private String content;

    private int category;

    private CreateTicketDTO[] tickets;
}
