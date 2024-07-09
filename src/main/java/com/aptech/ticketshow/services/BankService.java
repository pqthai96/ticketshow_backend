package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.BankDTO;

import java.util.List;

public interface BankService {

    List<BankDTO> findAll();

    BankDTO findById(Long id);

    BankDTO create(BankDTO bankDTO);

    BankDTO update(BankDTO bankDTO);

    void delete(Long id);
}
