package com.smartshop.controller;

import com.smartshop.dto.request.ClientRequest;
import com.smartshop.dto.response.ClientResponse;
import com.smartshop.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
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
}


