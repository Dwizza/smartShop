package com.smartshop.dto.Response;

import com.smartshop.entities.Enums.UserRole;
import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private UserRole role; // ADMIN_CLIENT, CLIENT
}
