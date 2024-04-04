package com.aptech.ticketshow.data.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class CategoryDTO {

    private Long id;

    private String name;

    private String description;

    private Double serviceCharge;

    private Date createdAt;

    private Date updatedAt;
}