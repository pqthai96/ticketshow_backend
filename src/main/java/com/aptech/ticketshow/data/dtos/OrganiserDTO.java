package com.aptech.ticketshow.data.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class OrganiserDTO {

    private Long id;

    private UserDTO userDTO;

    private String name;

    private String description;

    private String individualName;

    private Long individualTax;

    private Long individualIdentify;

    private String individualEmail;

    private String individualAddress;

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

    private Long defaultBankId;

    private Date createdAt;

    private Date updatedAt;
}
