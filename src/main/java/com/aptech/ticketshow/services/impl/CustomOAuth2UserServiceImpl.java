package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.entities.Status;
import com.aptech.ticketshow.data.entities.User;
import com.aptech.ticketshow.data.repositories.StatusRepository;
import com.aptech.ticketshow.data.repositories.UserRepository;
import com.aptech.ticketshow.services.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserServiceImpl extends DefaultOAuth2UserService implements CustomOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");
        String email = oAuth2User.getAttribute("email");
        String firstName = oAuth2User.getAttribute("given_name");
        String lastName = oAuth2User.getAttribute("family_name");
        String pictureUrl = oAuth2User.getAttribute("picture");

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAvatarImagePath(pictureUrl);
            user.setProvider(provider);
            user.setProviderId(providerId);
            user.setVerified(true);
        } else {
            user = new User();
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAvatarImagePath(pictureUrl);
            user.setProvider(provider);
            user.setProviderId(providerId);
            user.setVerified(true);

            Status activeStatus = statusRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Status not found"));
            user.setStatus(activeStatus);
        }

        userRepository.save(user);

        return oAuth2User;
    }
}