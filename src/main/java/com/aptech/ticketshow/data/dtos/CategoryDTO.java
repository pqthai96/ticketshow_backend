package com.aptech.ticketshow.data.dtos;

import lombok.Data;

@Data
public class CategoryDTO {

    private Long id;

    private String name;

    private String description;

    private Double serviceCharge;
}