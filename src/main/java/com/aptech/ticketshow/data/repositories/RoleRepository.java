package com.aptech.ticketshow.data.repositories;

import com.aptech.ticketshow.data.entities.ERole;
import com.aptech.ticketshow.data.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Object> findByName(ERole eRole);
}
