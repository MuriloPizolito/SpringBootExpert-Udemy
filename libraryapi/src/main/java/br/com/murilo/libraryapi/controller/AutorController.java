package br.com.murilo.libraryapi.controller;

import br.com.murilo.libraryapi.controller.dto.AutorDTO;
import br.com.murilo.libraryapi.controller.mappers.AutorMapper;
import br.com.murilo.libraryapi.model.Autor;
import br.com.murilo.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores") // o / é opcional
@RequiredArgsConstructor
public class AutorController implements GenericController {

    //Camada rest - view. Trata de receber as requisições e o roteamento para o service e retornar a reposta com código adequado

    private final AutorService autorService;
    private final AutorMapper mapper;

//    public AutorController(AutorService autorService) {
//        this.autorService = autorService;
//    }

    @PostMapping  //@Valid - valida os dados do dto, sem essa anotação a validação do dto não ocorre
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO autorDTO) { // usando o dto, pois na classe autor original tem campos que não virão preenchidos na requisição do cliente
        Autor autor = mapper.toEntity(autorDTO);
        autorService.salvar(autor);

        // http://localhost:8080/autores/550e8400-e29b-41d4-a716-446655440000
        URI location = gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(location).build(); // retornando com status created e com a URI
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        return autorService
                .obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = mapper.toDto(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());

//        if (autorOptional.isPresent()) {
//            Autor autor = autorOptional.get();
//            AutorDTO dto = mapper.toDto(autor);
//            return ResponseEntity.ok(dto);
//        }
//
//        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        autorService.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(value = "nome", required = false) String nome,
                                                    @RequestParam(value = "nacionalidade", required = false) String nacionalidade) { // nao sao obrigatórios
        List<Autor> resultadoPesquisa = autorService.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultadoPesquisa
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> atualizar(@PathVariable("id") String id,
                                            @RequestBody @Valid AutorDTO dto) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setDataNascimento(dto.dataNascimento());
        autor.setNacionalidade(dto.nacionalidade());

        autorService.atualizar(autor);

        return ResponseEntity.noContent().build();

    }

}
