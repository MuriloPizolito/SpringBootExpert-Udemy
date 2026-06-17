package br.com.murilo.libraryapi.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import javax.management.relation.Role;
import java.util.List;

public record UsuarioDto(
        @NotBlank(message = "campo obrigatório")
        String login,

        @NotBlank(message = "campo obrigatório")
        String senha,

        @Email(message = "inválido")
        @NotBlank(message = "campo obrigatório")
        String email,

        List<String> roles) {
}
