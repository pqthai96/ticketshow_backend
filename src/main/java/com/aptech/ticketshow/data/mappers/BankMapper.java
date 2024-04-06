package com.aptech.ticketshow.data.mappers;

import com.aptech.ticketshow.data.entities.Bank;
import com.aptech.ticketshow.data.dtos.BankDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        UserMapper.class
})
public interface BankMapper {

    Bank toEntity(BankDTO bankDTO);

    BankDTO toDTO(Bank bank);
}
