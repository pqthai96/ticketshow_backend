package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.OrganiserDTO;
import com.aptech.ticketshow.data.dtos.request.ModifyOrganiserRequest;
import com.aptech.ticketshow.data.entities.Organiser;
import com.aptech.ticketshow.data.mappers.OrganiserMapper;
import com.aptech.ticketshow.data.repositories.OrganiserRepository;
import com.aptech.ticketshow.services.ImageUploadService;
import com.aptech.ticketshow.services.OrganiserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrganiserServiceImpl implements OrganiserService {

    @Autowired
    private OrganiserRepository organiserRepository;

    @Autowired
    private OrganiserMapper organiserMapper;

    @Autowired
    private ImageUploadService imageUploadService;

    @Override
    public List<OrganiserDTO> findAll() {
        return organiserRepository.findAll().stream()
                .map(organiserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrganiserDTO findById(Long id) {
        Optional<Organiser> organiserOptional = organiserRepository.findById(id);
        if (organiserOptional.isPresent()) {
            return organiserMapper.toDTO(organiserOptional.get());
        } else {
            throw new RuntimeException("Organiser not found with id: " + id);
        }
    }

    @Override
    public OrganiserDTO create(ModifyOrganiserRequest modifyOrganiserRequest, MultipartFile avatarImage) {
        OrganiserDTO newOrganiserDTO = new OrganiserDTO();
        newOrganiserDTO.setName(modifyOrganiserRequest.getName());
        newOrganiserDTO.setDescription(modifyOrganiserRequest.getDescription());

        OrganiserDTO createdOrganiserDTO = organiserMapper.toDTO(organiserRepository.save(organiserMapper.toEntity(newOrganiserDTO)));

        String avatarImagePath = imageUploadService.uploadOrganiserAvatarImage(avatarImage, createdOrganiserDTO.getId().toString());
        createdOrganiserDTO.setAvatarImagePath(avatarImagePath);

        return organiserMapper.toDTO(organiserRepository.save(organiserMapper.toEntity(createdOrganiserDTO)));
    }

    @Override
    public OrganiserDTO edit(ModifyOrganiserRequest modifyOrganiserRequest, MultipartFile avatarImage) {
        OrganiserDTO editOrganiserDTO = organiserMapper.toDTO(organiserRepository.findById(modifyOrganiserRequest.getId()).orElse(null));

        editOrganiserDTO.setName(modifyOrganiserRequest.getName());
        editOrganiserDTO.setDescription(modifyOrganiserRequest.getDescription());

        if (avatarImage != null) {
            String avatarImagePath = imageUploadService.uploadOrganiserAvatarImage(avatarImage, editOrganiserDTO.getId().toString());
            editOrganiserDTO.setAvatarImagePath(avatarImagePath);
        }
        return organiserMapper.toDTO(organiserRepository.save(organiserMapper.toEntity(editOrganiserDTO)));
    }
}
