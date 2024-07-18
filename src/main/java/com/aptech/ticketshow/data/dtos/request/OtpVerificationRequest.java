package com.aptech.ticketshow.data.dtos.request;

import lombok.Data;

@Data
public class OtpVerificationRequest {
    private String phoneNumber;
    private String otp;
}
