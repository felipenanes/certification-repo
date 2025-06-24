package com.nnsgroup.certification.users.web;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeController {
    @GetMapping("/home")
    public String home(Principal principal) {
        if (principal != null) {
            return "Olá, " + principal.getName();
        } else {
            return "Bem-vindo, visitante!";
        }
    }

    @GetMapping("/home-auth")
    public String homeAuth(Authentication auth) {
        return "Usuário logado: " + auth.getName() + " | Roles: " + auth.getAuthorities();
    }

    @GetMapping("/test")
    public String test() {
        return "Aplicação funcionando!";
    }
}