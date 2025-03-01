package com.aptech.ticketshow.exception_v2;

public class TicketExceptionV2 extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TicketExceptionV2(String message) {
        super(message);
    }

    public TicketExceptionV2(String message, Throwable cause) {
        super(message, cause);
    }
}