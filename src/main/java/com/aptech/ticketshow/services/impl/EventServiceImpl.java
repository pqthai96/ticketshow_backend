package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.EventDTO;
import com.aptech.ticketshow.data.entities.*;
import com.aptech.ticketshow.data.mappers.AdminMapper;
import com.aptech.ticketshow.data.mappers.CategoryMapper;
import com.aptech.ticketshow.data.mappers.EventMapper;
import com.aptech.ticketshow.data.repositories.*;
import com.aptech.ticketshow.services.EventService;
import com.aptech.ticketshow.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private OrganiserRepository organiserRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public List<EventDTO> findAll() {
        return eventRepository.findAll().stream().map(r -> eventMapper.toDTO(r)).collect(Collectors.toList());
    }

    @Override
    public EventDTO findByID(Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            return eventMapper.toDTO(eventOptional.get());
        } else {
            throw new RuntimeException("Event not found with id: " + id);
        }
    }

    @Override
    public void addEvent(EventDTO eventDTO, MultipartFile bannerImg, MultipartFile positionImg) {
        Event event = eventMapper.toEntity(eventDTO);

        // Save the images
        if (bannerImg != null && !bannerImg.isEmpty()) {
            String bannerImgPath = saveFile(bannerImg);
            event.setBannerImagePath(bannerImgPath);
        }

        if (positionImg != null && !positionImg.isEmpty()) {
            String positionImgPath = saveFile(positionImg);
            event.setPositionImagePath(positionImgPath);
        }

        event = eventRepository.save(event);
        eventMapper.toDTO(event);
    }

    private String saveFile(MultipartFile file) {
        fileStorageService.save(file);
        return file.getOriginalFilename();
    }

    @Override
    public void deleteEvent(Long id) {
        Optional<Event> EventOptional = eventRepository.findById(id);
        if (EventOptional.isPresent()) {
            eventRepository.deleteById(id);
        } else {
            throw new RuntimeException("Event not found with id: " + id);
        }
    }

    @Override
    public EventDTO updateEvent(EventDTO eventDTO) {
        Optional<Event> eventOptional = eventRepository.findById(eventDTO.getId());
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            event.setTitle(eventDTO.getTitle());
            event.setVenueName(eventDTO.getVenueName());
            event.setLocationAddress(eventDTO.getLocationAddress());
            event.setLocationDistrict(eventDTO.getLocationDistrict());
            event.setLocationWard(eventDTO.getLocationWard());
            event.setLocationGooglePlaceId(eventDTO.getLocationGooglePlaceId());
            event.setLocationProvince((eventDTO.getLocationProvince()));
            event.setBarcode(eventDTO.getTitle());
            event.setStartedAt(eventDTO.getStartedAt());
            event.setEndedAt(eventDTO.getEndedAt());
            event.setOnSaleAt(eventDTO.getOnSaleAt());
            event.setPositionImagePath(eventDTO.getPositionImagePath());
            event.setBannerImagePath(eventDTO.getBannerImagePath());
            event.setContent((eventDTO.getContent()));
            event = eventRepository.save(event);
            return eventMapper.toDTO(event);
        } else {
            throw new RuntimeException("Event not found with id: " + eventDTO.getId());
        }
    }
}
