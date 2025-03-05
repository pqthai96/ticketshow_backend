package com.aptech.ticketshow.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.aptech.ticketshow.common.config.JwtUtil;
import com.aptech.ticketshow.data.dtos.UserDTO;
import com.aptech.ticketshow.data.dtos.request.VoucherRedeemRequest;
import com.aptech.ticketshow.data.dtos.response.VoucherRedeemResponse;
import com.aptech.ticketshow.data.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aptech.ticketshow.data.dtos.VoucherDTO;
import com.aptech.ticketshow.services.VoucherService;

@RestController
@RequestMapping("api/voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public ResponseEntity<List<VoucherDTO>> findAll() {
        List<VoucherDTO> vouchers = voucherService.findAll();
        return new ResponseEntity<>(vouchers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherDTO> findById(@PathVariable("id") Long id) {
        VoucherDTO voucherDTO = voucherService.findById(id);
        if (voucherDTO != null) {
            return new ResponseEntity<>(voucherDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<List<VoucherDTO>> findActiveVouchers() {
        Date currentDate = new Date();

        List<VoucherDTO> activeVouchers = voucherService.findActiveVouchers(currentDate);

        return new ResponseEntity<>(activeVouchers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VoucherDTO> create(@RequestBody VoucherDTO voucherDTO) {
        VoucherDTO createdVoucher = voucherService.create(voucherDTO);
        return new ResponseEntity<>(createdVoucher, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoucherDTO> update(@PathVariable("id") Long id, @RequestBody VoucherDTO voucherDTO) {
        voucherDTO.setId(id);
        VoucherDTO updatedVoucher = voucherService.update(voucherDTO);
        if (updatedVoucher != null) {
            return new ResponseEntity<>(updatedVoucher, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        voucherService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> findByCode(@PathVariable("code") String code) {
        return ResponseEntity.ok(voucherService.findByCode(code));
    }

    @PostMapping("/redeem-voucher")
    public ResponseEntity<VoucherRedeemResponse> redeemVoucher(@RequestHeader("Authorization") String token, @RequestBody VoucherRedeemRequest voucherRedeemRequest) {
        UserDTO userDTO = jwtUtil.extractUser(token);

        VoucherDTO voucherDTO = voucherService.findByCode(voucherRedeemRequest.getVoucherCode());

        if (voucherDTO == null) {
            return ResponseEntity.ok(new VoucherRedeemResponse(false, "Voucher is not present!", "Mã giảm giá không tồn tại!"));
        }

        // Check min order total
        if (voucherRedeemRequest.getOrderTotal() < voucherDTO.getMinOrderTotal()) {
            return ResponseEntity.ok(new VoucherRedeemResponse(false, "Order total does not meet the minimum requirement for voucher usage.", "Đơn hàng chưa đạt giá trị tối thiểu để sử dụng mã giảm giá"));
        }

        // Check time
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startedAt = convertDateToLocalDateTime(voucherDTO.getStartedAt());
        LocalDateTime endedAt = convertDateToLocalDateTime(voucherDTO.getEndedAt());

        if (now.isBefore(startedAt) || now.isAfter(endedAt)) {
            return ResponseEntity.ok(new VoucherRedeemResponse(false, "Current time is not within the valid voucher usage period.", "Thời gian hiện tại không nằm trong thời gian có thể sử dụng mã giảm giá."));
        }

        // Check voucher is already use
        if (!orderRepository.findByVoucherId(voucherDTO.getId()).isEmpty()) {
            return ResponseEntity.ok(new VoucherRedeemResponse(false, "This voucher has already been used.", "Mã giảm giá này đã được bạn sử dụng/"));
        }

        return ResponseEntity.ok(new VoucherRedeemResponse(true, "Redeem Voucher Succesfully!", "Quy đổi mã giảm giá thành công!"));
    }

    private LocalDateTime convertDateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
