package com.aptech.ticketshow.data.dtos;

import com.aptech.ticketshow.data.entities.Bank;
import com.aptech.ticketshow.data.entities.User;

import java.util.List;

public class HostingOrganiserDTO {

    private Long id;

    private User user;

    private String name;

    private String description;

    private String individualName;

    private Long individualTax;

    private String individualAddress;

    private String businessName;

    private Long businessTax;

    private String businessAddress;

    private List<Bank> banks;
}
