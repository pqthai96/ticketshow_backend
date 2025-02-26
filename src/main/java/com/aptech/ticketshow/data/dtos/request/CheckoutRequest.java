package com.aptech.ticketshow.data.dtos.request;

import com.aptech.ticketshow.data.dtos.CartDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequest {

    private Long eventId;

    private String currency;

    private List<String> seats;

    private List<CartDTO> cartDTOs;

    private Long voucherId;
}
