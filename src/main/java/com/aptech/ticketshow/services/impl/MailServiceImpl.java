package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.MailDTO;
import com.aptech.ticketshow.services.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public boolean sendMailWithAttachment(MailDTO mailDTO, byte[] pdfData, String pdfFileName) {
        return sendMail(mailDTO, pdfData, pdfFileName);
    }

    @Override
    public boolean sendMailWithToken(MailDTO mailDTO, String token) {
        String verificationLink = "http://localhost:8080/api/auth/verify?token=" + token;
        mailDTO.body = mailDTO.body +  "<p><a href=\"" + verificationLink + "\">Click here to verify</a></p>";
        return sendMail(mailDTO, null, null);
    }

    @Override
    public boolean sendMail(MailDTO mailDTO, byte[] attachmentData, String attachmentFileName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("support@ovation.com");
            helper.setTo(mailDTO.to);
            helper.setSubject(mailDTO.subject);

            String htmlBody = buildEmailBody(mailDTO);
            helper.setText(htmlBody, true);

            if (attachmentData != null && attachmentFileName != null) {
                ByteArrayDataSource dataSource = new ByteArrayDataSource(attachmentData, "application/octet-stream");
                dataSource.setName(attachmentFileName);
                helper.addAttachment(attachmentFileName, dataSource);
            }

            mailSender.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String buildEmailBody(MailDTO mailDTO) {
        return String.format("""
                <html>
                <body>
                    <img src="https://i.ibb.co/bMmt5rD3/ovation-black.png" alt="Ovation" height="50">
                    <p>Hi %s,</p>
                    <p>%s</p>
                    <p>Regards,</p>

                    <br><br><br>

                    <p style="font-weight: bold">OVATION TICKET SHOW BOOKING</p>
                    <p>Head Office: 275 Nguyen Van Dau Street, Binh Thanh District, Ho Chi Minh City, Viet Nam</p>
                    <p>Email: support@ovation.com</p>
                    <p>Customer Service: 090909090909</p>
                </body>
                </html>
                """, mailDTO.name, mailDTO.body);
    }
}
