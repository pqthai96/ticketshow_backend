package com.aptech.ticketshow.data.dtos;

import com.aptech.ticketshow.data.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankDTO {
    private Long id;

    private String ownerName;

    private Long number;

    private Date validDate;

    private Integer cvc;

    private String country;

    private Long zip;

    private UserDTO user;
}
