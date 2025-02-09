//package com.aptech.ticketshow.services.impl;
//
//import com.aptech.ticketshow.common.config.TwilioConfig;
//import com.aptech.ticketshow.services.SmsService;
//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class SmsServiceImpl implements SmsService {
//    private TwilioConfig twilioConfig;
//
//    @Autowired
//    public void SmsService(TwilioConfig twilioConfig) {
//        this.twilioConfig = twilioConfig;
//        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
//    }
//
//    public void sendSms(String to, String message) {
//        Message.creator(
//                new PhoneNumber(to),
//                new PhoneNumber(twilioConfig.getPhoneNumber()),
//                message
//        ).create();
//    }
//}
