package com.aptech.ticketshow.data.dtos;

import lombok.Data;

@Data
public class AdminDTO {
    private Long id;
    
    private RoleDTO roleDTO;

    private String password;

    private String name;
}
