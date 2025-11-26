package com.smartshop.service;

import com.smartshop.dto.request.ClientRequest;
import com.smartshop.dto.response.ClientResponse;
import com.smartshop.entity.Client;
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

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    public ClientResponse adminCreateClient(ClientRequest dto) {

        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new ValidationException("Username already exists");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .userRole(UserRole.CLIENT)
                .build();
        userRepository.save(user);

        Client client = Client.builder()
                .nom_complet(dto.getNom())
                .email(dto.getEmail())
                .tier(CustomerTier.BASIC)
                .totalOrders(0)
                .totalSpent(BigDecimal.ZERO)
                .user(user)
                .build();
        clientRepository.save(client);

        return toResponse(client, user);
    }


    public ClientResponse adminUpdateClient(Long id, ClientRequest dto) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        User user = client.getUser();

        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        userRepository.save(user);

        client.setNom_complet(dto.getNom());
        client.setEmail(dto.getEmail());
        clientRepository.save(client);

        return toResponse(client, user);
    }


    public void adminDeleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        userRepository.delete(client.getUser());
        clientRepository.delete(client);
    }

    public ClientResponse adminGetClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        return toResponse(client, client.getUser());
    }

    public List<ClientResponse> adminListClients() {
        return clientRepository.findAll()
                .stream()
                .map(c -> toResponse(c, c.getUser()))
                .toList();
    }

    private ClientResponse toResponse(Client client, User user) {
        return ClientResponse.builder()
                .id(client.getId())
                .username(user.getUsername())
                .nom(client.getNom_complet())
                .email(client.getEmail())
                .niveau(client.getTier())
                .totalOrders(client.getTotalOrders())
                .totalSpent(client.getTotalSpent())
                .build();
    }
}

