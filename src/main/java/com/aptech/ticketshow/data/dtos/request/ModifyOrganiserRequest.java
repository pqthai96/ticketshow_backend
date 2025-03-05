package com.aptech.ticketshow.data.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyOrganiserRequest {

    private Long id;

    private String name;

    private String description;

    private String avatarImagePath;
}
