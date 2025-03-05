package com.aptech.ticketshow.data.repositories;

import com.aptech.ticketshow.data.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByAdminName(String adminName);
}
