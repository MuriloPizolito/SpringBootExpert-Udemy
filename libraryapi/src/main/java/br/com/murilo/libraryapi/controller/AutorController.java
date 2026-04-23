package br.com.murilo.libraryapi.controller;

import br.com.murilo.libraryapi.model.Autor;
import br.com.murilo.libraryapi.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/autores") // o / é opcional
public class AutorController {

    @Autowired
    private AutorService autorService;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autorDTO) { // usando o dto, pois na classe autor original tem campos que não virão preenchidos na requisição do cliente

        Autor autorEntidade = autorDTO.mapearParaAutor();
        autorService.salvar(autorEntidade);

        // construindo a uri para a response - seguindo as regras do nosso contrato de api
        // http://localhost:8080/autores/550e8400-e29b-41d4-a716-446655440000
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorEntidade.getId())
                .toUri();

        return ResponseEntity.created(location).build(); // retornando com status created e com a URI
    }

}
