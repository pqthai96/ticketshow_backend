package com.aptech.ticketshow.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aptech.ticketshow.data.entities.Voucher;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long>{

    Voucher findByCode(String voucherCode);
}
