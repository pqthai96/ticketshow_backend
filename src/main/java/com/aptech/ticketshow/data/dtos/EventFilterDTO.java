package com.aptech.ticketshow.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class EventFilterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long statusId;

    private List<Object> locations;

    private List<Object> categories;

    private List<Object> dates;

    private List<Object> prices;

    private String sort;
}
