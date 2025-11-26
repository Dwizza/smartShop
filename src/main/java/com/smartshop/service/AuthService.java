package com.smartshop.service;

import com.smartshop.dto.request.ClientRegisterRequest;
import com.smartshop.dto.request.LoginRequest;
import com.smartshop.entity.User;
import com.smartshop.entity.enums.CustomerTier;
import com.smartshop.entity.enums.UserRole;
import com.smartshop.exception.ForbiddenException;
import com.smartshop.exception.UnauthorizedException;
import com.smartshop.exception.ValidationException;
import com.smartshop.repository.ClientRepository;
import com.smartshop.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.smartshop.entity.Client;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
//    private final

    public User login(LoginRequest dto) {

        return userRepository.findByUsername(dto.getUsername())
                .filter(u -> u.getPassword().equals(dto.getPassword()))
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    public void registerClient(ClientRegisterRequest dto, HttpSession session) {

        User current = (User) session.getAttribute("user");

        if (current == null) {
            throw new UnauthorizedException("Must login first");
        }

        if (current.getUserRole() != UserRole.ADMIN) {
            throw new ForbiddenException("Only admin can register a client");
        }

        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new ValidationException("Username already exists");
        }

        User newUser = User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .userRole(UserRole.CLIENT)
                .build();

        userRepository.save(newUser);

        Client client = Client.builder()
                .nom_complet(dto.getNom())
                .email(dto.getEmail())
                .tier(CustomerTier.BASIC)
                .totalOrders(0)
                .totalSpent(BigDecimal.ZERO)
                .user(newUser)
                .build();

        clientRepository.save(client);
    }
}
