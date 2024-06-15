package com.aptech.ticketshow;

import com.aptech.ticketshow.data.entities.ERole;
import com.aptech.ticketshow.data.entities.Status;
import com.aptech.ticketshow.data.entities.User;
import com.aptech.ticketshow.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TicketshowBackendApplication implements  CommandLineRunner{

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(TicketshowBackendApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		User adminAccount = userRepository.findByRole(ERole.ROLE_ADMIN);
		if(null == adminAccount){
			User user = new User();
			user.setId(1L);
			user.setStatus(new Status(1L,"Active"));
			user.setEmail("admin@gmail.com");
			user.setFirstName("Admin");
			user.setLastName("Super");
			user.setRole(ERole.ROLE_ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));

			userRepository.save(user);
		}
	}
}
