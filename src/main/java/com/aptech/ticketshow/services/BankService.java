package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.BankDTO;

import java.util.List;

public interface BankService {

    List<BankDTO> findAll();

    BankDTO findById(Long id);

    BankDTO save(BankDTO bankDTO);

    BankDTO update(BankDTO bankDTO);

    void deleteById(Long id);
}
