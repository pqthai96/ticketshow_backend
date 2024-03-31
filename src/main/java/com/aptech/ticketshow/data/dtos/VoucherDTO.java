package com.aptech.ticketshow.data.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDTO {

	private String description;

    private String name;

    private Double value;
    
    private Double minOrderTotal;

    private Date startedAt;

    private Date endedAt;

    private String code;
}

