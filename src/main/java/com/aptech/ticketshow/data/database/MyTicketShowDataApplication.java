//package com.aptech.ticketshow.data.database;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//
//@SpringBootApplication
//public class MyTicketShowDataApplication implements ApplicationContextAware {
//    private static ApplicationContext context;
//
//    public static void main(String[] args) {
//        SpringApplication.run(MyTicketShowDataApplication.class, args);
//
//        // Call the data seeding service (optional)
//        TicketShowDataService ticketShowDataService = context.getBean(TicketShowDataService.class);
//        ticketShowDataService.insertTicketShowData();
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) {
//        context = applicationContext;
//    }
//}