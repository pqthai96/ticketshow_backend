package com.aptech.ticketshow.util;

import com.aptech.ticketshow.data.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UtilCommon {

    public static User getUserByToken(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = auth.getPrincipal().toString();
        UserDetails userDetail = (UserDetails) auth.getCredentials();
        User user =  new User();
        user.setId(Long.parseLong(id));
        user.setFirstName(userDetail.getUsername());
        return user;
    }
}
