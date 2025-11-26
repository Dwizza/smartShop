package com.smartshop.dto.request;

import com.smartshop.entity.enums.CustomerTier;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String nom;

    @NotBlank
    private String email;
}

