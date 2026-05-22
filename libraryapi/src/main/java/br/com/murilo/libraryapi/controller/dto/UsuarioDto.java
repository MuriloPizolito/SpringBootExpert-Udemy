package br.com.murilo.libraryapi.controller.dto;

import javax.management.relation.Role;
import java.util.List;

public record UsuarioDto(
        String login,
        String senha,
        List<String> roles) {
}
