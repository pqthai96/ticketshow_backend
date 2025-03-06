package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.EventDTO;
import com.aptech.ticketshow.data.dtos.EventFilterDTO;
import com.aptech.ticketshow.data.dtos.PaginationDTO;
import com.aptech.ticketshow.data.dtos.TicketDTO;
import com.aptech.ticketshow.data.dtos.request.ModifyEventRequest;
import com.aptech.ticketshow.data.entities.Event;
import com.aptech.ticketshow.data.entities.Order;
import com.aptech.ticketshow.data.entities.OrderItem;
import com.aptech.ticketshow.data.mappers.EventMapper;
import com.aptech.ticketshow.data.repositories.EventRepository;
import com.aptech.ticketshow.data.repositories.OrderItemRepository;
import com.aptech.ticketshow.data.repositories.OrderRepository;
import com.aptech.ticketshow.data.repositories.specification.EventSpecification;
import com.aptech.ticketshow.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private OrganiserService organiserService;

    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public PaginationDTO findAll(int no, int limit) {
        Page<EventDTO> page = eventRepository.findAll(PageRequest.of(no, limit)).map(r -> eventMapper.toDTO(r));
        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(), page.getTotalPages(), page.getTotalElements(), page.getSize(), page.getNumber());
    }

    @Override
    public PaginationDTO findAllByStatus(int no, int limit, Long statusId) {
        Page<EventDTO> page = eventRepository.findAll(EventSpecification.filterEventWithStatus(new EventFilterDTO(), statusId), PageRequest.of(no, limit)).map(r -> eventMapper.toDTO(r));
        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(), page.getTotalPages(), page.getTotalElements(), page.getSize(), page.getNumber());
    }

    @Override
    public PaginationDTO findAllByCategory(int no, int limit, Long categoryId) {
        Page<EventDTO> page = eventRepository.findByCategoryIdOrderByEndedAtDesc(categoryId, PageRequest.of(no, limit)).map(r -> eventMapper.toDTO(r));
        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(), page.getTotalPages(), page.getTotalElements(), page.getSize(), page.getNumber());
    }

    @Override
    public EventDTO findById(Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            EventDTO eventDTO = eventMapper.toDTO(eventOptional.get());
            eventDTO.setTickets(ticketService.findByEventId(eventDTO.getId()));
            return eventDTO;
        } else {
            throw new RuntimeException("Event not found with id: " + id);
        }
    }

    @Override
    public EventDTO create(ModifyEventRequest modifyEventRequest, MultipartFile bannerImage, MultipartFile positionImage) {
        EventDTO newEventDTO = new EventDTO();

        newEventDTO.setCategoryDTO(categoryService.findById(modifyEventRequest.getCategoryId()));
        newEventDTO.setStatusDTO(statusService.findById(modifyEventRequest.getStatusId()));
        newEventDTO.setOrganiserDTO(organiserService.findById(modifyEventRequest.getOrganiserId()));
        newEventDTO.setTitle(modifyEventRequest.getTitle());
        newEventDTO.setStartedAt(modifyEventRequest.getStartedAt());
        newEventDTO.setEndedAt(modifyEventRequest.getEndedAt());
        newEventDTO.setContent(modifyEventRequest.getContent());
        newEventDTO.setLocationAddress(modifyEventRequest.getLocationAddress());
        newEventDTO.setLocationWard(modifyEventRequest.getLocationWard());
        newEventDTO.setLocationDistrict(modifyEventRequest.getLocationDistrict());
        newEventDTO.setLocationProvince(modifyEventRequest.getLocationProvince());
        newEventDTO.setVenueName(modifyEventRequest.getVenueName());
        newEventDTO.setType(modifyEventRequest.isType());

        EventDTO createdEventDTO = eventMapper.toDTO(eventRepository.save(eventMapper.toEntity(newEventDTO)));

        String bannerImagePath = imageUploadService.uploadBannerImage(bannerImage, createdEventDTO.getId().toString());

        createdEventDTO.setBannerImagePath(bannerImagePath);

        if (createdEventDTO.isType()) {
            createdEventDTO.setPositionImagePath(imageUploadService.uploadPositionImage(positionImage, createdEventDTO.getId().toString()));
            for (TicketDTO ticketDTO : modifyEventRequest.getTicketDTOs()) {
                ticketDTO.setEventDTO(createdEventDTO);
                ticketService.create(ticketDTO);
            }
        } else {
            createdEventDTO.setSeatPrice(modifyEventRequest.getSeatPrice());
            createdEventDTO.setBookedSeats(modifyEventRequest.getBookedSeats());
            createdEventDTO.setAllSeats("[\"A1\", \"A2\", \"A3\", \"A4\", \"A5\", \"A6\", \"A7\", \"A8\", \"A9\", \"B1\", \"B2\", \"B3\", \"B4\", \"B5\", \"B6\", \"B7\", \"B8\", \"B9\", \"C1\", \"C2\", \"C3\", \"C4\", \"C5\", \"C6\", \"C7\", \"C8\", \"C9\", \"D1\", \"D2\", \"D3\", \"D4\", \"D5\", \"D6\", \"D7\", \"D8\", \"D9\", \"E1\", \"E2\", \"E3\", \"E4\", \"E5\", \"E6\", \"E7\", \"E8\", \"E9\", \"F1\", \"F2\", \"F3\", \"F4\", \"F5\", \"F6\", \"F7\", \"F8\", \"F9\", \"G1\", \"G2\", \"G3\", \"G4\", \"G5\", \"G6\", \"G7\", \"G8\", \"G9\", \"H1\", \"H2\", \"H3\", \"H4\", \"H5\", \"H6\", \"H7\", \"H8\", \"H9\", \"I1\", \"I2\", \"I3\", \"I4\", \"I5\", \"I6\", \"I7\", \"I8\", \"I9\", \"J1\", \"J2\", \"J3\", \"J4\", \"J5\", \"J6\", \"J7\", \"J8\", \"J9\", \"K1\", \"K2\", \"K3\", \"K4\", \"K5\", \"K6\", \"K7\", \"K8\", \"K9\", \"L1\", \"L2\", \"L3\", \"L4\", \"L5\", \"L6\", \"L7\", \"L8\", \"L9\"]");
        }

        return eventMapper.toDTO(eventRepository.save(eventMapper.toEntity(createdEventDTO)));
    }

    @Override
    public EventDTO edit(ModifyEventRequest modifyEventRequest, MultipartFile bannerImage, MultipartFile positionImage) {
        EventDTO editEventDTO = eventMapper.toDTO(eventRepository.findById(modifyEventRequest.getId()).orElse(null));

        editEventDTO.setCategoryDTO(categoryService.findById(modifyEventRequest.getCategoryId()));
        editEventDTO.setStatusDTO(statusService.findById(modifyEventRequest.getStatusId()));
        editEventDTO.setOrganiserDTO(organiserService.findById(modifyEventRequest.getOrganiserId()));
        editEventDTO.setTitle(modifyEventRequest.getTitle());
        editEventDTO.setStartedAt(modifyEventRequest.getStartedAt());
        editEventDTO.setEndedAt(modifyEventRequest.getEndedAt());
        editEventDTO.setContent(modifyEventRequest.getContent());
        editEventDTO.setLocationAddress(modifyEventRequest.getLocationAddress());
        editEventDTO.setLocationWard(modifyEventRequest.getLocationWard());
        editEventDTO.setLocationDistrict(modifyEventRequest.getLocationDistrict());
        editEventDTO.setLocationProvince(modifyEventRequest.getLocationProvince());
        editEventDTO.setVenueName(modifyEventRequest.getVenueName());
        editEventDTO.setType(modifyEventRequest.isType());

        EventDTO editedEventDTO = eventMapper.toDTO(eventRepository.save(eventMapper.toEntity(editEventDTO)));

        if (bannerImage != null) {
            String bannerImagePath = imageUploadService.uploadBannerImage(bannerImage, editedEventDTO.getId().toString());
            editedEventDTO.setBannerImagePath(bannerImagePath);
        }


        if (editedEventDTO.isType()) {
            if (positionImage != null) {
                editedEventDTO.setPositionImagePath(imageUploadService.uploadPositionImage(positionImage, editedEventDTO.getId().toString()));
            }
            for (TicketDTO ticketDTO : modifyEventRequest.getTicketDTOs()) {
                ticketService.update(ticketDTO);
            }
        } else {
            editedEventDTO.setSeatPrice(modifyEventRequest.getSeatPrice());
        }

        return eventMapper.toDTO(eventRepository.save(eventMapper.toEntity(editedEventDTO)));
    }

    @Override
    public EventDTO update(EventDTO eventDTO) {
        return eventMapper.toDTO(eventRepository.save(eventMapper.toEntity(eventDTO)));
    }

    @Override
    public PaginationDTO filter(int no, int limit, EventFilterDTO eventFilterDTO) {
        if (eventFilterDTO.getStatusId() == null) {
            eventFilterDTO.setStatusId(1L);
        }
        Page<EventDTO> page = eventRepository.findAll(EventSpecification.filterEvent(eventFilterDTO), PageRequest.of(no, limit))
                .map(item -> eventMapper.toDTO(item));
        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(), page.getTotalPages(), page.getTotalElements(), page.getSize(), page.getNumber());
    }

    /* NEW SEARCH start */
    @Override
    public PaginationDTO search(int no, int limit, String searchValue) {
        Page<EventDTO> page = eventRepository.findAllActive(PageRequest.of(no, limit)).map(r -> eventMapper.toDTO(r));
        String[] keywords = searchValue.toLowerCase().split("\\s+");

        List<EventDTO> filteredEventDTOs = page.getContent().stream()
                .filter(event -> {
                    String title = removeDiacritics(event.getTitle().toLowerCase());
                    return Arrays.stream(keywords)
                            .allMatch(title::contains);
                })
                .collect(Collectors.toList());

        return new PaginationDTO(filteredEventDTOs, page.isFirst(), page.isLast(), page.getTotalPages(), page.getTotalElements(), page.getSize(), page.getNumber());
    }

    public static String removeDiacritics(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{M}");
        return pattern.matcher(normalized).replaceAll("");
    }
    /* NEW SEARCH end */

    @Override
    public EventDTO bookedSeat(EventDTO eventDTO) {
        Optional<Event> eventOptional = eventRepository.findById(eventDTO.getId());
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            event.setBookedSeats(eventDTO.getBookedSeats());
            event = eventRepository.save(event);
            return eventMapper.toDTO(event);
        } else {
            throw new RuntimeException("Event not found with id: " + eventDTO.getId());
        }
    }

    @Override
    public PaginationDTO findRecentEvents(int no, int limit) {
        Page<EventDTO> page = eventRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(no, limit)).map(r -> eventMapper.toDTO(r));
        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(), page.getTotalPages(), page.getTotalElements(), page.getSize(), page.getNumber());
    }

    @Override
    public PaginationDTO findBestSellingEvents(int no, int limit) {
        Page<EventDTO> page = eventRepository.findBestSellingEvents(PageRequest.of(no, limit)).map(r -> eventMapper.toDTO(r));
        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(), page.getTotalPages(), page.getTotalElements(), page.getSize(), page.getNumber());
    }

    @Override
    public Integer ticketsAndSeatsCount(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));

        if (event.isType()) {
            List<Order> orders = orderRepository.findByEventIdAndStatusId(event.getId(), 1L);

            if (orders.isEmpty()) {
                return 0;
            }

            List<String> orderIds = orders.stream()
                    .map(Order::getId)
                    .collect(Collectors.toList());

            return orderItemRepository.findByOrderIdIn(orderIds).stream()
                    .mapToInt(OrderItem::getQuantity)
                    .sum();

        } else {
            String bookedSeats = event.getBookedSeats();

            if (bookedSeats == null || bookedSeats.isEmpty()) {
                return 0;
            }

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String[] seatsArray = objectMapper.readValue(bookedSeats, String[].class);
                return seatsArray.length;
            } catch (Exception e) {
                throw new RuntimeException("Error parsing bookedSeats data: " + e.getMessage());
            }
        }
    }
}
