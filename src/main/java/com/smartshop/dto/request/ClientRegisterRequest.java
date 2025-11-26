package com.smartshop.dto.request;

import lombok.Data;

@Data
public class ClientRegisterRequest {
    private String username;
    private String password;
    private String nom;
    private String email;
}

