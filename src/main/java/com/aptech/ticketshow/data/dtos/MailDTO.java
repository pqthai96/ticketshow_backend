package com.aptech.ticketshow.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDTO {

    public String name;

    public String to;

    public String subject;

    public String body;
}
