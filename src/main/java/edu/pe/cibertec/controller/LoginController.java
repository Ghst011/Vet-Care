package edu.pe.cibertec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }
}
// Este controller NO autentica a nadie, solo devuelve la vista.
// La autenticación real la hace Spring Security por debajo.