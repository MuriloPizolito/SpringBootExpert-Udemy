package br.com.murilo.libraryapi.controller;

import br.com.murilo.libraryapi.model.Client;
import br.com.murilo.libraryapi.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
@Tag(name = "Client")
@Slf4j
public class ClientController {

    private final ClientService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Salvar", description = "Cadastrar novo client.")
    public void salvar(@RequestBody Client client){
        log.info("Registrando novo Client: {} com scope: {}", client.getClientId(), client.getScope());

        //ideal é usar o dto, e nao a entidade direto
        service.salvar(client);
    }

}
