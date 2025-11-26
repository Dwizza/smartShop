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

    @Before("execution(* com.smartshop.controller..admin*(..))")
    public void adminOnly() {
        User user = (User) session.getAttribute("user");
        if (user == null)
            throw new UnauthorizedException("Login required");
        if (user.getUserRole() != UserRole.ADMIN)
            throw new ForbiddenException("Admin only");
    }

    @Before("execution(* com.smartshop.controller..client*(..))")
    public void clientOnly() {
        User user = (User) session.getAttribute("user");
        if (user == null)
            throw new UnauthorizedException("Login required");
        if (user.getUserRole() != UserRole.CLIENT)
            throw new ForbiddenException("Client only");
    }

    @Before("execution(* com.smartshop.controller..auth*(..))")
    public void authenticatedOnly() {
        User user = (User) session.getAttribute("user");
        if (user == null)
            throw new UnauthorizedException("Login required");
    }
}
