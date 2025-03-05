package com.aptech.ticketshow.controllers;

import com.aptech.ticketshow.data.dtos.OrganiserDTO;
import com.aptech.ticketshow.data.dtos.request.ModifyOrganiserRequest;
import com.aptech.ticketshow.services.OrganiserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/organiser")
@RequiredArgsConstructor
public class OrganiserController {

    @Autowired
    private OrganiserService organiserService;

    @GetMapping
    public ResponseEntity<List<OrganiserDTO>> getOrganisers() {
        List<OrganiserDTO> organizers = organiserService.findAll();
        return ResponseEntity.ok(organizers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganiserDTO> getOrganiserById(@PathVariable Long id) {
        OrganiserDTO organizer = organiserService.findById(id);
        return ResponseEntity.ok(organizer);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createOrganiser(HttpServletRequest httpServletRequest,
                                             @RequestPart(value = "avatarImage", required = true) MultipartFile avatarImage) {
        try {
            Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();

            ModifyOrganiserRequest modifyOrganiserRequest = new ModifyOrganiserRequest();

            if (parameterMap.containsKey("name")) modifyOrganiserRequest.setName(parameterMap.get("name")[0]);
            if (parameterMap.containsKey("description")) modifyOrganiserRequest.setDescription(parameterMap.get("description")[0]);

            OrganiserDTO createdOrganiserDTO = organiserService.create(modifyOrganiserRequest, avatarImage);
            return ResponseEntity.ok(createdOrganiserDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request: " + e.getMessage());
        }
    }

    @PostMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editOrganiser(HttpServletRequest httpServletRequest,
                                             @RequestPart(value = "avatarImage", required = false) MultipartFile avatarImage) {
        try {
            Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();

            ModifyOrganiserRequest modifyOrganiserRequest = new ModifyOrganiserRequest();

            if (parameterMap.containsKey("id")) modifyOrganiserRequest.setId(Long.valueOf(parameterMap.get("id")[0]));
            if (parameterMap.containsKey("name")) modifyOrganiserRequest.setName(parameterMap.get("name")[0]);
            if (parameterMap.containsKey("description")) modifyOrganiserRequest.setDescription(parameterMap.get("description")[0]);

            OrganiserDTO editedOrganiserDTO = organiserService.edit(modifyOrganiserRequest, avatarImage);
            return ResponseEntity.ok(editedOrganiserDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request: " + e.getMessage());
        }
    }

}