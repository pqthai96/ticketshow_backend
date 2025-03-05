package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.UserDTO;
import com.aptech.ticketshow.data.dtos.UserProfileDTO;
import com.aptech.ticketshow.data.entities.User;
import com.aptech.ticketshow.data.mappers.UserMapper;
import com.aptech.ticketshow.data.repositories.UserRepository;
import com.aptech.ticketshow.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(r -> userMapper.toDTO(r)).collect(Collectors.toList());

    }

    @Override
    public UserDTO findById(Long id) {
        return userMapper.toDTO(userRepository.findById(id).orElse(null));
    }

    @Override
    public UserProfileDTO editUser(UserProfileDTO userProfileDTO) {
        return null;
    }

    @Override
    public UserDTO verifyEmailUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO changePasswordUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserProfileDTO findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.map(user -> userMapper.toProfileDTO(user)).orElse(null);
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        return userMapper.toDTO(userRepository.save(userMapper.toEntity(userDTO)));
    }

    @Override
    public UserDTO updateProfile(UserDTO userDTO, UserProfileDTO userProfileDTO) {
        userDTO.setAddress(userProfileDTO.getAddress());
        userDTO.setFirstName(userProfileDTO.getFirstName());
        userDTO.setLastName(userProfileDTO.getLastName());
        userDTO.setGender(userProfileDTO.isGender());
        userDTO.setDayOfBirth(userProfileDTO.getDayOfBirth());
        userDTO.setProvince(userProfileDTO.getProvince());
        userDTO.setDistrict(userProfileDTO.getDistrict());
        userDTO.setWard(userProfileDTO.getWard());

        return userMapper.toDTO(userRepository.save(userMapper.toEntity(userDTO)));
    }

    @Override
    public UserDTO findUserByVerificationToken(String verificationToken) {
        return userMapper.toDTO(userRepository.findUserByVerificationToken(verificationToken));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        if (user.getStatus().getId() != 1) {
            throw new UsernameNotFoundException("User account is not active");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                "",
                true,
                true,
                true,
                true,
                authorities
        );
    }
}
