package com.aptech.ticketshow.data.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherRedeemRequest {

    private String voucherCode;

    private long orderTotal;
}
