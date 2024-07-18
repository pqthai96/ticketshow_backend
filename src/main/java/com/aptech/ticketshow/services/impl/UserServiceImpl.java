package com.aptech.ticketshow.services.impl;

import com.aptech.ticketshow.data.dtos.UserDTO;
import com.aptech.ticketshow.data.dtos.UserProfileDTO;
import com.aptech.ticketshow.data.entities.User;
import com.aptech.ticketshow.data.mappers.UserMapper;
import com.aptech.ticketshow.data.repositories.UserRepository;
import com.aptech.ticketshow.exception_v2.ResourceNotFoundExceptionV2;
import com.aptech.ticketshow.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userMapper.toDTO(userOptional.get());
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    @Override
    public UserProfileDTO editUser(UserProfileDTO userProfileDTO){
        Long id = userProfileDTO.getId();
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setPhone(userProfileDTO.getPhone());
            user.setFirstName(userProfileDTO.getFirstName());
            user.setLastName(userProfileDTO.getLastName());
            user.setAddress(userProfileDTO.getAddress());
            user.setDistrict(userProfileDTO.getDistrict());
            user.setProvince(userProfileDTO.getProvince());
            user.setWard(userProfileDTO.getWard());
            user = userRepository.save(user);
            return userMapper.toProfileDTO(user);
        }
        return userProfileDTO;
    }

    @Override
    public UserDTO verifyEmailUser(UserDTO userDTO){
        Long id = userDTO.getId();
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setEmailVerified(userDTO.getEmailVerified());
            user = userRepository.save(user);
            return userMapper.toDTO(user);
        }
        return userDTO;
    }

    @Override
    public UserDTO changePasswordUser(UserDTO userDTO){
        Long id = userDTO.getId();
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setPassword(userDTO.getPassword());
            user = userRepository.save(user);
            return userMapper.toDTO(user);
        }
        return userDTO;
    }

    @Override
    public UserDTO findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDTO)
                .orElse(null);
    }

    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return user;
    }

//    @Override
//    public UserDTO findById(Long id) {
//        return userMapper.toDTO(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExceptionV2(Collections.singletonMap("id", id))));
//    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        return userMapper.toDTO(userRepository.save(userMapper.toEntity(userDTO)));
    }

    public UserDTO update(UserDTO userDTO) {
        User oldUser = userRepository.findById(userDTO.getId()).orElseThrow(() -> new ResourceNotFoundExceptionV2(Collections.singletonMap("id", userDTO.getId())));

        return null;
    }
}
