package com.aptech.ticketshow.data.dtos;


import com.aptech.ticketshow.data.entities.Event;
import com.aptech.ticketshow.data.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganiserDTO {

    private Long id;

    private String name;

    private String description;

    private String avatarImagePath;

    private List<Event> events;
}
