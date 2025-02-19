package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.MailDTO;

public interface MailService {

    boolean sendMailWithAttachment(MailDTO mailDTO, byte[] pdfData, String pdfFileName);

    boolean sendMailWithToken(MailDTO mailDTO, String token);

    boolean sendMail(MailDTO mailDTO, byte[] attachmentData, String attachmentFileName);
}
