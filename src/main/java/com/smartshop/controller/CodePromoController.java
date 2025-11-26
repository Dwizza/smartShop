package com.smartshop.controller;

import com.smartshop.dto.request.CodePromoRequest;
import com.smartshop.dto.response.CodePromoResponse;
import com.smartshop.service.CodePromoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/code-promo")
@RequiredArgsConstructor
public class CodePromoController {

    private final CodePromoService codePromoService;

    @PostMapping("/create")
    public ResponseEntity<CodePromoResponse> adminCreate(@RequestBody CodePromoRequest dto) {
        return ResponseEntity.ok(codePromoService.adminCreate(dto));
    }

    @PutMapping("/update/{id}")
    public CodePromoResponse adminUpdate(@PathVariable Long id, @RequestBody CodePromoRequest dto) {
        return codePromoService.adminUpdate(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void adminDelete(@PathVariable Long id) {
        codePromoService.adminDelete(id);
    }

    @GetMapping("/all")
    public List<CodePromoResponse> adminList() {
        return codePromoService.adminList();
    }

    @GetMapping("/verify/{code}")
    public CodePromoResponse adminVerify(@PathVariable String code) {
        return codePromoService.adminVerify(code);
    }
}

