package com.aptech.ticketshow.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
	
	private Long id;

	private String email;

    private String subject;

    private String content;

    private String adminReply;

    private AdminDTO adminDTO;

    private StatusDTO statusDTO;

    private Date createdAt;

    private Date updatedAt;
}
