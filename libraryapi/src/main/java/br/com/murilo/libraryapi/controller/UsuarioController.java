package br.com.murilo.libraryapi.controller;

import br.com.murilo.libraryapi.controller.dto.UsuarioDto;
import br.com.murilo.libraryapi.controller.mappers.UsuarioMapper;
import br.com.murilo.libraryapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuário")
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Salvar", description = "Salvar novo usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso.")
    })
    public void salvar(@RequestBody @Valid UsuarioDto dto) {
        var usuario = mapper.toEntity(dto);
        service.salvar(usuario);
    }

}
