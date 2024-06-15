package com.aptech.ticketshow.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteDTO {
	
	private Long id;

    private Long eventId;
    private Long userId;
	private EventDTO eventDTO;

    private UserDTO userDTO;
}
