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

        CommandeResponse response = commandeService.adminCreateCommande(request);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<CommandeResponse> adminGetCommandeById(@PathVariable Long id) {
//        return ResponseEntity.ok(commandeService.adminGetCommandeById(id));
//    }

//    @GetMapping("/all")
//    public ResponseEntity<List<CommandeResponse>> adminGetAllCommandes() {
//        return ResponseEntity.ok(commandeService.adminGetAllCommandes());
//    }

//    @PutMapping("/{id}/status")
//    public ResponseEntity<CommandeResponse> adminUpdateStatus(
//            @PathVariable Long id,
//            @RequestParam OrderStatus statut) {
//
//        return ResponseEntity.ok(commandeService.adminUpdateStatus(id, statut));
//    }
}

