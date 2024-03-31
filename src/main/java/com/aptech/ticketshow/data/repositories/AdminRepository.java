package com.aptech.ticketshow.data.repositories;

import com.aptech.ticketshow.data.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Role, Long> {
}
