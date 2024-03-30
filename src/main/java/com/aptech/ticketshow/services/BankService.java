package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.AdminDTO;
import com.aptech.ticketshow.data.dtos.BankDTO;

import java.util.List;

public interface BankService {

    List<BankDTO> findAll();
}
