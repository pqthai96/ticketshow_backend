package com.aptech.ticketshow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TicketshowBackendApplication{

	public static void main(String[] args) {
		SpringApplication.run(TicketshowBackendApplication.class, args);
		
	}
}
