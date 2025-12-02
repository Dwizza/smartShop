package com.smartshop.controller;

import com.smartshop.dto.request.CommandeRequest;
import com.smartshop.dto.response.CommandeResponse;
import com.smartshop.service.CommandeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
@RequiredArgsConstructor
public class CommandeController {

    private final CommandeService commandeService;

    @PostMapping("/create")
    public ResponseEntity<CommandeResponse> adminCreateCommande(
            @Valid @RequestBody CommandeRequest request) {

        CommandeResponse response = commandeService.CreateCommande(request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/confirm")
    public CommandeResponse adminConfirmCommande(@PathVariable Long id) {
        return commandeService.adminConfirmCommande(id);
    }

    @PatchMapping("{id}/cancel")
    public ResponseEntity<CommandeResponse> adminCancelCommande(@PathVariable Long id) {
        return ResponseEntity.ok(commandeService.CancelCommande(id));
    }

}

