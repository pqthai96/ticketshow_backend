package com.aptech.ticketshow.data.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class VoucherDTO {
    private Long id;

    private Date createdAt;

    private Date updatedAt;

    private String description;

    private String name;

    private Double value;

    private Double minOrderTotal;

    private Date startedAt;

    private Date endedAt;

    private String code;
}