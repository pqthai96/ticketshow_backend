package com.aptech.ticketshow.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {

    private long id;

    private String phone;

    private String firstName;

    private String lastName;

    private String address;

    private String district;

    private String province;

    private String ward;
}
