package com.smartshop.controller;

import com.smartshop.dto.request.ClientRegisterRequest;
import com.smartshop.dto.request.LoginRequest;
import com.smartshop.entity.User;
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
    public ResponseEntity<String> login(@RequestBody LoginRequest dto, HttpSession session) {

        User user = authService.login(dto);
        session.setAttribute("user", user);

        return ResponseEntity.ok("Logged in successfully");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        authService.logout(session);
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/register-client")
    public ResponseEntity<String> registerClient(@RequestBody ClientRegisterRequest dto, HttpSession session) {

        authService.registerClient(dto, session);
        return ResponseEntity.ok("Client registered successfully");
    }
}
