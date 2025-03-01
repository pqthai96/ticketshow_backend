package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.request.CheckoutRequest;

public interface TicketValidationService {

    boolean validateTicketAvailability(CheckoutRequest checkoutRequest);
}
