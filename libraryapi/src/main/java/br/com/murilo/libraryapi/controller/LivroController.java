package br.com.murilo.libraryapi.controller;

import br.com.murilo.libraryapi.controller.dto.CadastroLivroDto;
import br.com.murilo.libraryapi.controller.dto.ResultadoPesquisaLivroDto;
import br.com.murilo.libraryapi.controller.mappers.LivroMapper;
import br.com.murilo.libraryapi.model.GeneroLivro;
import br.com.murilo.libraryapi.model.Livro;
import br.com.murilo.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @GetMapping
    public ResponseEntity<List<ResultadoPesquisaLivroDto>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nomeAutor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "anoPublicacao", required = false)
            Integer anoPublicacao
    ) {
        var resultado = livroService.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao);
        var lista = resultado
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);

    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id,
            @RequestBody @Valid CadastroLivroDto dto){

        return livroService.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    Livro entidadeAuxiliar = mapper.toEntity(dto);

                    livro.setDataPublicacao(entidadeAuxiliar.getDataPublicacao());
                    livro.setIsbn(entidadeAuxiliar.getIsbn());
                    livro.setPreco(entidadeAuxiliar.getPreco());
                    livro.setGenero(entidadeAuxiliar.getGenero());
                    livro.setTitulo(entidadeAuxiliar.getTitulo());
                    livro.setAutor(entidadeAuxiliar.getAutor());

                    livroService.atualizar(livro);
                    return ResponseEntity.noContent().build();

                }).orElseGet(() -> ResponseEntity.notFound().build());


    }

}
