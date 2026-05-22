package br.com.murilo.libraryapi.controller;

import br.com.murilo.libraryapi.controller.dto.UsuarioDto;
import br.com.murilo.libraryapi.controller.mappers.UsuarioMapper;
import br.com.murilo.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDto dto) {
        var usuario = mapper.toEntity(dto);
        service.salvar(usuario);
    }

}
