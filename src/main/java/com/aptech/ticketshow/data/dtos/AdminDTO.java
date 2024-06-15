package com.aptech.ticketshow.data.dtos;

import com.aptech.ticketshow.data.entities.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {
    private Long id;

    private ERole role;

    private String password;

    private String name;
}
