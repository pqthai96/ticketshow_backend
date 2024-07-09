package com.aptech.ticketshow.data.dtos;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class EventDTO {
    private Long id;

    private String title;

    private String venueName;

    private String locationAddress;

    private String locationProvince;

    private String locationDistrict;

    private String locationWard;

    private String locationGooglePlaceId;

    private String barcode;

    private Date startedAt;

    private Date endedAt;

    private Date onSaleAt;

    private String positionImagePath;

    private String bannerImagePath;

    private String content;

    private OrganiserDTO organiser;

    private CategoryDTO category;

    private AdminDTO editedByAdminId;

    private List<TicketDTO> tickets;
}
