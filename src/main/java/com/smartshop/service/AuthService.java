package com.smartshop.service;

import com.smartshop.dto.request.LoginRequest;
import com.smartshop.entity.User;
import com.smartshop.entity.enums.CustomerTier;
import com.smartshop.entity.enums.UserRole;
import com.smartshop.exception.ForbiddenException;
import com.smartshop.exception.ResourceNotFoundException;
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

    public String login(LoginRequest dto, HttpSession session) {

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Nom d'utilisateur ou mot de passe invalide"));

        if (!user.getPassword().equals(dto.getPassword())) {
            throw new UnauthorizedException("Nom d'utilisateur ou mot de passe invalide");
        }

        session.setAttribute("user", user);

        if (user.getUserRole() == UserRole.CLIENT) {
            Client client = clientRepository.findByUser(user)
                    .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé pour cet utilisateur"));

            session.setAttribute("client", client);
        }

        return "Connexion réussie";
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

}
