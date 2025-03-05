package com.aptech.ticketshow.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aptech.ticketshow.data.entities.Voucher;

import java.util.Date;
import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long>{

    Voucher findByCode(String voucherCode);

    @Query("SELECT v FROM Voucher v WHERE v.startedAt <= :currentDate AND v.endedAt > :currentDate")
    List<Voucher> findActiveVouchers(@Param("currentDate") Date currentDate);
}
