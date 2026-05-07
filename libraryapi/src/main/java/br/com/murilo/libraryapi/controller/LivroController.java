package br.com.murilo.libraryapi.controller;

import br.com.murilo.libraryapi.controller.dto.CadastroLivroDto;
import br.com.murilo.libraryapi.controller.dto.ResultadoPesquisaLivroDto;
import br.com.murilo.libraryapi.controller.mappers.LivroMapper;
import br.com.murilo.libraryapi.model.Livro;
import br.com.murilo.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService livroService;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDto dto) {
        Livro livro = mapper.toEntity(dto); // mapear dto para a entidade
        livroService.salvar(livro); // enviar a entidade para o service validar e salvar na base
        var url = gerarHeaderLocation(livro.getId()); // criar url para acesso dos dados do livro
        return ResponseEntity.created(url).build(); // retornar código created com header location
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDto> obterDetalhes(@PathVariable("id") String id) {
        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDto(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    livroService.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }

}
