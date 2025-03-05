package com.aptech.ticketshow.controllers;

import com.aptech.ticketshow.common.config.JwtUtil;
import com.aptech.ticketshow.data.dtos.AdminDTO;
import com.aptech.ticketshow.data.dtos.request.AdminLoginRequest;
import com.aptech.ticketshow.data.dtos.response.AdminLoginResponse;
import com.aptech.ticketshow.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<AdminDTO>> findAll() {
        List<AdminDTO> admins = adminService.findAll();
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> findById(@PathVariable Long id) {
        AdminDTO admin = adminService.findById(id);
        return ResponseEntity.ok(admin);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminLoginRequest adminLoginRequest) {
        AdminDTO adminDTO = adminService.findByAdminName(adminLoginRequest.getAdminName());

        if (adminDTO != null) {
            if(passwordEncoder.matches(adminLoginRequest.getPassword(), adminDTO.getPassword())) {
                String token = jwtUtil.generateAdminToken(adminDTO.getAdminName());

                return ResponseEntity.ok(new AdminLoginResponse(token, adminDTO.getFullName()));
            }
        }
        return ResponseEntity.status(401).body("Wrong admin name or password");
    }
}
