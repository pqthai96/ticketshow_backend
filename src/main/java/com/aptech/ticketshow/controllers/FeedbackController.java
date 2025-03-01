package com.aptech.ticketshow.controllers;

import java.net.URI;
import java.util.List;

import com.aptech.ticketshow.data.dtos.StatusDTO;
import com.aptech.ticketshow.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.ticketshow.data.dtos.FeedbackDTO;
import com.aptech.ticketshow.services.FeedbackService;

@RestController
@RequestMapping("api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private StatusService statusService;
    
    @GetMapping
    public ResponseEntity<List<FeedbackDTO>> findAll() {
        List<FeedbackDTO> feedbacks = feedbackService.findAll();
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO> findById(@PathVariable Long id) {
        FeedbackDTO feedback = feedbackService.findById(id);
        return ResponseEntity.ok(feedback);
    }

    @PostMapping
    public ResponseEntity<FeedbackDTO> create(@RequestBody FeedbackDTO feedbackDTO) {
        feedbackDTO.setStatusDTO(statusService.findById(4L));
        return ResponseEntity.ok(feedbackService.create(feedbackDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackDTO> update(@RequestBody FeedbackDTO feedbackDTO) {
        FeedbackDTO updatedFeedback = feedbackService.update(feedbackDTO);
        return ResponseEntity.ok(updatedFeedback);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        feedbackService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
