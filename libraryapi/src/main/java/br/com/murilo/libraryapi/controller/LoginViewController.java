package br.com.murilo.libraryapi.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //diferente do rest controller. Esse (@Controller) é para páginas web
public class LoginViewController {

    @GetMapping("/login")
    public String paginaLogin(){
        return "login";
    }

    @GetMapping("/")
    @ResponseBody // nao vai esperar o retorno de uma página
    public String paginaHome(Authentication authentication){
        return "Olá " + authentication.getName();
    }

}
