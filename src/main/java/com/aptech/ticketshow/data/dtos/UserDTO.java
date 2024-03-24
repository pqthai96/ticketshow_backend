package com.aptech.ticketshow.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private long id;

    private StatusDTO statusDTO;

    private RoleDTO roleDTO;

    private String phone;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String address;

    private String district;

    private String province;

    private String ward;

    private String confirmationCode;

    private Boolean isConfirm;

    private String rememberToken;
}
