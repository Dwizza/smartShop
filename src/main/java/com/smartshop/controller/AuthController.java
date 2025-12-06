package com.smartshop.controller;

import com.smartshop.dto.request.LoginRequest;
import com.smartshop.entity.Client;
import com.smartshop.entity.User;
import com.smartshop.exception.ResourceNotFoundException;
import com.smartshop.repository.ClientRepository;
import com.smartshop.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> AuthLogin(@RequestBody LoginRequest dto, HttpSession session) {

        String message = authService.login(dto, session);

        return ResponseEntity.ok(message);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> AuthLogout(HttpSession session) {
        authService.logout(session);
        return ResponseEntity.ok("Logged out successfully");
    }
}
