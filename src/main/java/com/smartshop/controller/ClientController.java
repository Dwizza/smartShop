package com.smartshop.controller;

import com.smartshop.dto.request.ClientRequest;
import com.smartshop.dto.response.ClientProfileResponse;
import com.smartshop.dto.response.ClientResponse;
import com.smartshop.entity.Client;
import com.smartshop.service.ClientService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<ClientResponse> adminCreateClient(@RequestBody ClientRequest dto) {
        return ResponseEntity.ok(clientService.adminCreateClient(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClientResponse> adminUpdateClient(@PathVariable Long id, @RequestBody ClientRequest dto) {
        return ResponseEntity.ok(clientService.adminUpdateClient(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> adminDeleteClient(@PathVariable Long id) {
        clientService.adminDeleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> adminGetClient(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.adminGetClient(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ClientResponse>> adminListClients() {
        return ResponseEntity.ok(clientService.adminListClients());
    }

    @GetMapping("/me")
    public ResponseEntity<ClientProfileResponse> clientProfile(HttpSession session) {
        Client client = (Client) session.getAttribute("client");
        return ResponseEntity.ok(clientService.getClientProfile(client));
    }
}


