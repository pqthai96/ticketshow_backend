package com.aptech.ticketshow.data.dtos.request;

import com.aptech.ticketshow.data.dtos.TicketDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyEventRequest {

    private Long id;

    private String title;

    private String venueName;

    private boolean type;

    private String locationAddress;

    private String locationProvince;

    private String locationDistrict;

    private String locationWard;

    private Date startedAt;

    private Date endedAt;

    private String content;

    private String allSeats;

    private String bookedSeats;

    private Double seatPrice;

    private Long organiserId;

    private Long statusId;

    private Long categoryId;

    private List<TicketDTO> ticketDTOs;
}
