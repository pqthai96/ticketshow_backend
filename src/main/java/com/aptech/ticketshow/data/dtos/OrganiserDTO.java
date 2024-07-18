package com.aptech.ticketshow.data.dtos;


import com.aptech.ticketshow.data.entities.Bank;
import com.aptech.ticketshow.data.entities.Event;
import com.aptech.ticketshow.data.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganiserDTO {

    private Long id;

    private UserDTO userDTO;

    private String name;

    private String description;

    private String avatarImagePath;
  
    private String individualName;

    private Long individualTax;

    private Long individualIdentify;

    private String individualEmail;

    private String individualAddress;

    private String individualProvince;

    private String individualDistrict;

    private String individualWard;

    private String businessName;

    private Long businessTax;

    private String businessIssuePlace;

    private Date businessIssueDate;

    private String businessPhone;

    private String businessEmail;

    private String businessAddress;

    private String businessProvince;

    private String businessDistrict;

    private String businessWard;

    private Double balance;

    private User user;

    private List<Bank> banks;

    private List<Event> events;

    private Long defaultBankId;
}
