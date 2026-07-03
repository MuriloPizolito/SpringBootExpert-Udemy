package br.com.murilo.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import javax.management.relation.Role;
import java.util.List;

@Schema(name = "Usuario")
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
