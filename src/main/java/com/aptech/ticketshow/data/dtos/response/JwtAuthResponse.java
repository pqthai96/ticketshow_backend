package com.aptech.ticketshow.data.dtos.response;

import com.aptech.ticketshow.data.entities.ERole;
import lombok.Data;

import java.util.Date;

@Data
public class JwtAuthResponse {

    private long id;
    private ERole role;
    private String phone;
    private String email;
    private String firstName;
    private String lastName;
    private String name;
    private Date emailVerified;
    private String token;
    private String refreshToken;

    public JwtAuthResponse() {
        this.name = firstName + " " + lastName;
        this.id = getId();
    }
}
