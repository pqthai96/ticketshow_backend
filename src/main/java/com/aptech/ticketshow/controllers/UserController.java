package com.aptech.ticketshow.controllers;

import com.aptech.ticketshow.data.dtos.UserDTO;
import com.aptech.ticketshow.data.dtos.UserProfileDTO;
import com.aptech.ticketshow.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/get-user-by-id/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/edit-user-by-id/{id}")
    public ResponseEntity<UserProfileDTO> editUserById(@PathVariable Long id, @RequestBody UserProfileDTO userProfileDTO){
        userProfileDTO.setId(id);
        UserProfileDTO editedUser = userService.editUser(userProfileDTO);
        return ResponseEntity.ok(editedUser);
    }

    @GetMapping("/get-user-by-email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        UserDTO user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/verify-email-user/{id}")
    public ResponseEntity<UserDTO> verifyEmail(@PathVariable Long id, @RequestBody UserDTO userDTO){
        userDTO.setId(id);
        UserDTO editedUser = userService.verifyEmailUser(userDTO);
        return ResponseEntity.ok(editedUser);
    }

    @PutMapping("/change-password-user/{id}")
    public ResponseEntity<UserDTO> changePasswordUserById(@PathVariable Long id, @RequestBody UserDTO userDTO){
        userDTO.setId(id);
        UserDTO editedUser = userService.changePasswordUser(userDTO);
        return ResponseEntity.ok(editedUser);
    }
}
