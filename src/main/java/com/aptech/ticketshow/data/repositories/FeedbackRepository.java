package com.aptech.ticketshow.data.repositories;

import com.aptech.ticketshow.data.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long>{

}
