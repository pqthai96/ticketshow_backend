package com.aptech.ticketshow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.aptech.ticketshow.data.database.TicketShowDataService;

@SpringBootApplication
public class TicketshowBackendApplication {

	private static ApplicationContext context;
	
	public static void main(String[] args) {
		SpringApplication.run(TicketshowBackendApplication.class, args);
		
//		TicketShowDataService ticketShowDataService = context.getBean(TicketShowDataService.class);
//        ticketShowDataService.insertTicketShowData();
	}
	public void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }
}
