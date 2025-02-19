package com.aptech.ticketshow.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {

    private long id;

    private String email;

    private String phone;

    private String firstName;

    private String lastName;

    private boolean gender;

    private String address;

    private String district;

    private String province;

    private String ward;

    private boolean isVerified;

    private Date dayOfBirth;
}
