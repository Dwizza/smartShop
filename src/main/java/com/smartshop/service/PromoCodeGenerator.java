package com.smartshop.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class PromoCodeGenerator {

    private static final String PREFIX = "PROMO-";
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public String generate() {
        StringBuilder sb = new StringBuilder(PREFIX);
        for (int i = 0; i < 4; i++) {
            sb.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
        return sb.toString();
    }
}
