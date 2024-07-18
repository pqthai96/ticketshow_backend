package com.aptech.ticketshow.services;

import java.util.List;

import com.aptech.ticketshow.data.dtos.FeedbackDTO;

public interface FeedbackService {

	List<FeedbackDTO> findAll();

    FeedbackDTO findById(Long id);

    FeedbackDTO create(FeedbackDTO feedbackDTO);

    FeedbackDTO update(FeedbackDTO feedbackDTO);

    void delete(Long id);
}
