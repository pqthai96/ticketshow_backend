package com.aptech.ticketshow.data.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String email;

    private String firstName;

    private String lastName;

    private String token;

    private String message;
}