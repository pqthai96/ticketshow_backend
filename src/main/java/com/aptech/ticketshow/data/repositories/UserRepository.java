package com.aptech.ticketshow.data.repositories;

import com.aptech.ticketshow.data.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Page<User> findAll(Pageable pageable);

    User findUserByVerificationToken(String verificationToken);

    Optional<User> findByEmailAndForgotPasswordToken(String email, String forgotPasswordToken);
}
