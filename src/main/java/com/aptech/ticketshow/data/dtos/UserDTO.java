package com.aptech.ticketshow.data.dtos;

import com.aptech.ticketshow.data.entities.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends Auditable {

    private long id;

    private StatusDTO statusDTO;

    private String phone;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private boolean gender;

    private String address;

    private String district;

    private String province;

    private String ward;

    private String avatarImagePath;

    private boolean isVerified;

    private String verificationToken;

    private Date dayOfBirth;

    private String forgotPasswordToken;
}
