package com.aptech.ticketshow.data.dtos;

import java.util.Date;
import java.util.List;

import com.aptech.ticketshow.data.entities.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    private Long categoryId;
    private Long editedByAdminId;
    private Long organiserId;
    // private List<Ticket> tickets;

    private String bookedSeat;

    private OrganiserDTO organiser;

    private CategoryDTO category;

    private AdminDTO editedByAdmin;

    private List<TicketDTO> tickets;
}
