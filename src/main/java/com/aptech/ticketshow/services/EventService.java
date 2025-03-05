package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.EventDTO;
import com.aptech.ticketshow.data.dtos.EventFilterDTO;
import com.aptech.ticketshow.data.dtos.PaginationDTO;
import com.aptech.ticketshow.data.dtos.request.ModifyEventRequest;
import org.springframework.web.multipart.MultipartFile;

public interface EventService {

    PaginationDTO findAll(int no, int limit);

    PaginationDTO findAllByStatus(int no, int limit, Long statusId);

    EventDTO findById(Long id);

    EventDTO update(EventDTO eventDTO);

    PaginationDTO filter(int no, int limit, EventFilterDTO eventFilterDTO);

    PaginationDTO search(int no, int limit, String searchValue);

    EventDTO bookedSeat(EventDTO eventDTO);

    PaginationDTO findRecentEvents(int pageNo, int pageSize);

    PaginationDTO findBestSellingEvents(int pageNo, int pageSize);

    EventDTO create(ModifyEventRequest modifyEventRequest, MultipartFile bannerImage, MultipartFile positionImage);

    EventDTO edit(ModifyEventRequest modifyEventRequest, MultipartFile bannerImage, MultipartFile positionImage);

    PaginationDTO findAllByCategory(int no, int limit, Long categoryId);
}
