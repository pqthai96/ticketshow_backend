package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.StatusDTO;

public interface StatusService {

    StatusDTO findById(Long id);
}
