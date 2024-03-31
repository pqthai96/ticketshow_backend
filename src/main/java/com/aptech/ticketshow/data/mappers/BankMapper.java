package com.aptech.ticketshow.data.mappers;

import com.aptech.ticketshow.data.dtos.AdminDTO;
import com.aptech.ticketshow.data.dtos.BankDTO;
import com.aptech.ticketshow.data.entities.Admin;
import com.aptech.ticketshow.data.entities.Bank;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        UserMapper.class
})
public interface BankMapper {

    @Mapping(source = "userDTO", target = "user")
    Bank toEntity(BankDTO bankDTO);

    @Mapping(source = "user", target = "userDTO")
    BankDTO toDTO(Bank bank);
}
