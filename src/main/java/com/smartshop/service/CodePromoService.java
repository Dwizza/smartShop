package com.smartshop.service;

import com.smartshop.dto.request.CodePromoRequest;
import com.smartshop.dto.response.CodePromoResponse;
import com.smartshop.entity.CodePromo;
import com.smartshop.exception.ResourceNotFoundException;
import com.smartshop.exception.ValidationException;
import com.smartshop.mapper.CodePromoMapper;
import com.smartshop.repository.CodePromoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CodePromoService {

    private final CodePromoRepository codePromoRepository;
    private final CodePromoMapper codePromoMapper;

    public CodePromoResponse adminCreate(CodePromoRequest dto) {

        if (codePromoRepository.findByCode(dto.getCode()).isPresent()) {
            throw new ValidationException("Code promo already exists");
        }

        CodePromo promo = codePromoMapper.toEntity(dto);

        CodePromo saved = codePromoRepository.save(promo);

        return codePromoMapper.toResponse(saved);
    }


    public CodePromoResponse adminUpdate(Long id, CodePromoRequest dto) {

        CodePromo promo = codePromoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Code promo not found"));

        codePromoMapper.updateEntity(promo, dto);

        CodePromo saved = codePromoRepository.save(promo);

        return codePromoMapper.toResponse(saved);
    }

    public void adminDelete(Long id) {
        CodePromo promo = codePromoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Code promo not found"));
        codePromoRepository.delete(promo);
    }

    public List<CodePromoResponse> adminList() {
        return codePromoRepository.findAll()
                .stream()
                .map(codePromoMapper::toResponse)
                .toList();
    }

    public CodePromoResponse adminVerify(String code) {

        CodePromo promo = codePromoRepository.findByCode(code)
                .orElseThrow(() -> new ValidationException("Invalid promo code"));

        if (!promo.getActive())
            throw new ValidationException("Promo code disabled");

        if (promo.getExpirationDate().isBefore(LocalDate.now()))
            throw new ValidationException("Promo code expired");

        return codePromoMapper.toResponse(promo);
    }
}

