package com.aptech.ticketshow.data.database;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import com.aptech.ticketshow.data.entities.Admin;
import com.aptech.ticketshow.data.entities.Bank;
import com.aptech.ticketshow.data.entities.Category;
import com.aptech.ticketshow.data.entities.Discount;
import com.aptech.ticketshow.data.entities.Event;
import com.aptech.ticketshow.data.entities.Favorite;
import com.aptech.ticketshow.data.entities.Feedback;
import com.aptech.ticketshow.data.entities.Order;
import com.aptech.ticketshow.data.entities.OrderItem;
import com.aptech.ticketshow.data.entities.Organiser;
import com.aptech.ticketshow.data.entities.Role;
import com.aptech.ticketshow.data.entities.Status;
import com.aptech.ticketshow.data.entities.Ticket;
import com.aptech.ticketshow.data.entities.User;
import com.aptech.ticketshow.data.entities.Voucher;
import com.aptech.ticketshow.data.repositories.AdminRepository;
import com.aptech.ticketshow.data.repositories.BankRepository;
import com.aptech.ticketshow.data.repositories.CategoryRepository;
import com.aptech.ticketshow.data.repositories.DiscountRepository;
import com.aptech.ticketshow.data.repositories.FavoriteRepository;
import com.aptech.ticketshow.data.repositories.RoleRepository;
import com.aptech.ticketshow.data.repositories.StatusRepository;
import com.aptech.ticketshow.data.repositories.UserRepository;
import com.aptech.ticketshow.data.repositories.VoucherRepository;

@Service
public class TicketShowDataService {
	@Autowired
	private AdminRepository adminRepository;
	private BankRepository bankRepository;
	private CategoryRepository categoryRepository;
	private DiscountRepository discountRepository;
	private EventRepository eventRepository;
	private FavoriteRepository favoriteRepository;
	private FeedbackRepository feedbackRepository;
	private OrderRepository orderRepository;
	private OrderItemRepository orderItemRepository;
	private OrganiserRepository organiserRepository;
	private RoleRepository roleRepository;
	private StatusRepository statusRepository;
	private TicketRepository ticketRepository;
	private UserRepository userRepository;
	private VoucherRepository voucherRepository;
	
    public void insertDemoData() {
    	// Create your demo data objects
    	Role role1 = new Role((long) 1, null);
    	Status status1 = new Status((long) 1, null);
    	Voucher voucher1 = new Voucher((long) 1,"Deduct $10 from the payment session","Voucher $10", 10.0, 100.0, new Date(), new Date(), "110100");
    	Category category1 = new Category((long) 1, null, null, null);
    	User user1 = new User(1, status1, role1, null, null, null, null, null, null, null, null, null, null, null, null);
    	Organiser organiser1 = new Organiser(null, user1, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    	Admin admin1 = new Admin((long) 1, role1, null, null);
    	Event event1 = new Event((long) 1, null, null, null, null, null, null, null, null, null, null, null, null, null, null, organiser1, category1, admin1);
    	Ticket ticket1 = new Ticket((long) 1, null, null, null, null, null, null, null, event1, admin1);
        Discount discount1 = new Discount((long) 1, " 10% discount ticket", 0.9, new Date(), new Date(),ticket1);
        Discount discount2 = new Discount((long) 2, " 15% discount ticket", 0.85, new Date(), new Date(),ticket1);
        Favorite favorite1 = new Favorite(null, event1, user1);
        Bank bank1 = new Bank((long)1, null, null, null, null, null, null, user1);
        Order order1 = new Order((long)1, null, null, null, null, null, null, user1, event1, voucher1);
        OrderItem orderItem1 = new OrderItem((long)1, null, order1, ticket1, discount2);
        Feedback feedback1 = new Feedback((long)1, null, null, null, null, admin1);
        
        // Save the entities to the database
        roleRepository.save(role1);
        statusRepository.save(status1);
        voucherRepository.save(voucher1);
        categoryRepository.save(category1);
        userRepository.save(user1);
        organiserRepository.save(organiser1);
        adminRepository.save(admin1);
        eventRepository.save(event1);
        ticketRepository.save(ticket1);
        discountRepository.save(discount1);
        discountRepository.save(discount2);
        favoriteRepository.save(favorite1);
        bankRepository.save(bank1);
        orderRepository.save(order1);
        orderItemRepository.save(orderItem1);
        feedbackRepository.save(feedback1);
        

        System.out.println("TicketShow data inserted successfully!");
    }
}

public class MySpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySpringBootApplication.class, args);

        // Call the data seeding service (optional)
        TicketShowDataService seekDataService = (TicketShowDataService) SpringApplication.getContext().getBean("seekDataService");
        seekDataService.insertDemoData();
    }
}

