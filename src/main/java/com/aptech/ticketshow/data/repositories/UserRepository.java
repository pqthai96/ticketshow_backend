package com.aptech.ticketshow.data.repositories;

import com.aptech.ticketshow.data.entities.ERole;
import com.aptech.ticketshow.data.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User findByRole(ERole role);

    Page<User> findAll(Pageable pageable);
}
