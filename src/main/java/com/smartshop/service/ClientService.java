package com.smartshop.service;

import com.smartshop.dto.request.ClientRequest;
import com.smartshop.dto.response.ClientResponse;
import com.smartshop.entity.Client;
import com.smartshop.entity.User;
import com.smartshop.entity.enums.CustomerTier;
import com.smartshop.entity.enums.UserRole;
import com.smartshop.exception.ResourceNotFoundException;
import com.smartshop.exception.ValidationException;
import com.smartshop.mapper.ClientMapper;
import com.smartshop.repository.ClientRepository;
import com.smartshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

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

        return clientMapper.toResponse(client);
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

        return clientMapper.toResponse(client);
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

        return clientMapper.toResponse(client);
    }



    public List<ClientResponse> adminListClients() {
        return clientRepository.findAll()
                .stream()
                .map(c -> clientMapper.toResponse(c))
                .toList();
    }

    public BigDecimal calculateFidelityDiscount(Client client, BigDecimal sousTotal) {

        if (client.getTier() == CustomerTier.SILVER && sousTotal.compareTo(BigDecimal.valueOf(500)) >= 0) {
            return sousTotal.multiply(BigDecimal.valueOf(0.05));
        }

        if (client.getTier() == CustomerTier.GOLD && sousTotal.compareTo(BigDecimal.valueOf(800)) >= 0) {
            return sousTotal.multiply(BigDecimal.valueOf(0.10));
        }

        if (client.getTier() == CustomerTier.PLATINUM && sousTotal.compareTo(BigDecimal.valueOf(1000)) >= 0) {
            return sousTotal.multiply(BigDecimal.valueOf(0.15));
        }

        return BigDecimal.ZERO;
    }

    public ClientResponse UpdateTier(Client c) {
        Client client = clientRepository.findById(c.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        if (c.getTotalSpent().compareTo(BigDecimal.valueOf(1000)) >= 0 || c.getTotalOrders() >= 20) {
            client.setTier(CustomerTier.PLATINUM);
        } else if (c.getTotalSpent().compareTo(BigDecimal.valueOf(800)) >= 0 || c.getTotalOrders() >= 10) {
            client.setTier(CustomerTier.GOLD);
        } else if (c.getTotalSpent().compareTo(BigDecimal.valueOf(500)) >= 0 || c.getTotalOrders() >= 3) {
            client.setTier(CustomerTier.SILVER);
        } else {
            client.setTier(CustomerTier.BASIC);
        }
        clientRepository.save(client);

        return clientMapper.toResponse(client);
    }

}

