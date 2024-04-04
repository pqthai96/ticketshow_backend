package com.aptech.ticketshow.data.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class AdminDTO {
    private Long id;
    
    private RoleDTO roleDTO;

    private String password;

    private String name;

    private Date createdAt;

    private Date updatedAt;
}
