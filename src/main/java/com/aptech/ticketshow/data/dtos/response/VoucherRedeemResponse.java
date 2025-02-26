package com.aptech.ticketshow.data.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherRedeemResponse {

    private boolean success;

    private String enMessage;

    private String viMessage;
}
