package com.smartshop.bootstrap;

import com.smartshop.entity.User;
import com.smartshop.entity.enums.UserRole;
import com.smartshop.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;

    @PostConstruct
    public void initAdmin() {
        if(userRepository.findByUsername("admin").isPresent()){
            return;
        }

        User admin = User.builder()
                .username("admin")
                .password("admin123")
                .userRole(UserRole.ADMIN)
                .build();

        userRepository.save(admin);

        System.out.println("ADMIN user created automatically: admin / admin123");
    }
}

