package com.aptech.ticketshow.services;

import java.util.List;

import com.aptech.ticketshow.data.dtos.OrganiserDTO;
import com.aptech.ticketshow.data.dtos.request.ModifyOrganiserRequest;
import org.springframework.web.multipart.MultipartFile;

public interface OrganiserService {

    List<OrganiserDTO> findAll();

    OrganiserDTO findById(Long id);

    OrganiserDTO create(ModifyOrganiserRequest modifyOrganiserRequest, MultipartFile avatarImage);

    OrganiserDTO edit(ModifyOrganiserRequest modifyOrganiserRequest, MultipartFile avatarImage);
}
