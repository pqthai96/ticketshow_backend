package com.aptech.ticketshow.data.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class StatusDTO {

    private Long id;

    private Date createdAt;

    private Date updatedAt;

    private String name;
}