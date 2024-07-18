package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.BankDTO;
import com.aptech.ticketshow.data.entities.Bank;
import com.aptech.ticketshow.data.mappers.BankMapper;
import com.aptech.ticketshow.data.mappers.UserMapper;
import com.aptech.ticketshow.data.repositories.BankRepository;
import com.aptech.ticketshow.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BankMapper bankMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<BankDTO> findAll() {
        return bankRepository.findAll().stream().map(bankMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public BankDTO findById(Long id) {
        Optional<Bank> bankOptional = bankRepository.findById(id);
        if (bankOptional.isPresent()) {
            return bankMapper.toDTO(bankOptional.get());
        } else {
            throw new RuntimeException("Bank not found with id: " + id);
        }
    }

    @Override
    public BankDTO create(BankDTO bankDTO) {
        Bank bank = bankMapper.toEntity(bankDTO);
        bank = bankRepository.save(bank);
        return bankMapper.toDTO(bank);
    }

    @Override
    public BankDTO update(BankDTO bankDTO) {
        Long id = bankDTO.getId();
        Optional<Bank> bankOptional = bankRepository.findById(id);
        if (bankOptional.isPresent()) {
            Bank bank = bankOptional.get();
            bank.setOwnerName(bankDTO.getOwnerName());
            bank.setBankName(bankDTO.getBankName());
            bank.setAccountNumber(bankDTO.getAccountNumber());
            bank.setBankBranch(bankDTO.getBankBranch());
            bank.setValidDate(bankDTO.getValidDate());
            bank.setCvc(bankDTO.getCvc());
            bank.setCountry(bankDTO.getCountry());
            bank.setZip(bankDTO.getZip());
            bank.setUser(userMapper.toEntity(bankDTO.getUserDTO()));
            bank = bankRepository.save(bank);
            return bankMapper.toDTO(bank);
        } else {
            throw new RuntimeException("Bank not found with id: " + id);
        }
    }


    @Override
    public void delete(Long id) {
        Optional<Bank> bankOptional = bankRepository.findById(id);
        if (bankOptional.isPresent()) {
            bankRepository.deleteById(id);
        } else {
            throw new RuntimeException("Bank not found with id: " + id);
        }
    }
}
