package com.smartshop.security;

import com.smartshop.entity.User;
import com.smartshop.entity.enums.UserRole;
import com.smartshop.exception.ForbiddenException;
import com.smartshop.exception.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RoleAspect {

    private final HttpSession session;



    private User getLogged() {
        User user = (User) session.getAttribute("user");
        if (user == null)
            throw new UnauthorizedException("You must login first");
        return user;
    }

    private void requireAdmin() {
        User user = getLogged();
        if (user.getUserRole() != UserRole.ADMIN)
            throw new ForbiddenException("Admin only");
    }

    private void requireClient() {
        User user = getLogged();
        if (user.getUserRole() != UserRole.CLIENT)
            throw new ForbiddenException("Client only");
    }

    private void requireLogin() {
        getLogged();
    }
}

