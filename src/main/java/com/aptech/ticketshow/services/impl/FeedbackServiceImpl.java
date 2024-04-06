package com.aptech.ticketshow.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.ticketshow.data.dtos.FeedbackDTO;
import com.aptech.ticketshow.data.entities.Feedback;
import com.aptech.ticketshow.data.mappers.AdminMapper;
import com.aptech.ticketshow.data.mappers.FeedbackMapper;
import com.aptech.ticketshow.data.repositories.FeedbackRepository;
import com.aptech.ticketshow.services.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FeedbackMapper feedbackMapper;
    
    @Autowired
    private AdminMapper adminMapper;
    
    @Override
    public List<FeedbackDTO> findAll() {
        return feedbackRepository.findAll().stream().map(r -> feedbackMapper.toDTO(r)).collect(Collectors.toList());
    }

    @Override
    public FeedbackDTO findById(Long id) {
        Optional<Feedback> feedbackOptional = feedbackRepository.findById(id);
        if (feedbackOptional.isPresent()) {
            return feedbackMapper.toDTO(feedbackOptional.get());
        } else {
            throw new RuntimeException("Feedback not found with id: " + id);
        }
    }

    @Override
    public FeedbackDTO save(FeedbackDTO feedbackDTO) {
        Feedback feedback = feedbackMapper.toEntity(feedbackDTO);
        feedback = feedbackRepository.save(feedback);
        return feedbackMapper.toDTO(feedback);
    }

    @Override
    public FeedbackDTO update(FeedbackDTO feedbackDTO) {
    	long id = feedbackDTO.getId();
        Optional<Feedback> feedbackOptional = feedbackRepository.findById(id);
        if (feedbackOptional.isPresent()) {
            Feedback feedback = feedbackOptional.get();
            feedback.setEmail(feedbackDTO.getEmail());
            feedback.setSubject(feedbackDTO.getSubject());
            feedback.setContent(feedbackDTO.getContent());
            feedback.setAdminReply(feedbackDTO.getAdminReply());
            feedback.setAdmin(adminMapper.toEntity(feedbackDTO.getAdminDTO()));
            feedback = feedbackRepository.save(feedback);
            return feedbackMapper.toDTO(feedback);
        } else {
            throw new RuntimeException("Feedback not found with id: " + id);
        }
    }

    @Override
    public void deleteById(Long id) {
        Optional<Feedback> feedbackOptional = feedbackRepository.findById(id);
        if (feedbackOptional.isPresent()) {
            feedbackRepository.deleteById(id);
        } else {
            throw new RuntimeException("Feedback not found with id: " + id);
        }
    }

}
