package br.com.murilo.libraryapi.service;

import br.com.murilo.libraryapi.model.GeneroLivro;
import br.com.murilo.libraryapi.model.Livro;
import br.com.murilo.libraryapi.repository.LivroRepository;
import br.com.murilo.libraryapi.repository.specs.LivroSpecs;
import br.com.murilo.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// importando todos os métodos estáticos de LivroSpecs
import static br.com.murilo.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroValidator livroValidator;

    public Livro salvar(Livro livro) {
        livroValidator.validar(livro);
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro) {
        livroRepository.delete(livro);
    }

    public List<Livro> pesquisa(
            String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao) {

        // select * from livro where isbn = :isbn and nomeAutor =
//        Specification<Livro> specs = Specification
//                .where(LivroSpecs.isbnEqual(isbn))
//                .and(LivroSpecs.tituloLike(titulo))
//                .and(LivroSpecs.generoEqual(genero));

        // select * from livro where 0 = 0
        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction()); //conjunction é um critério verdadeiro

        if (isbn != null) {
            // query = query and isbn = :isbn
            specs = specs.and(isbnEqual(isbn));
        }

        if (titulo != null) {
            specs = specs.and(tituloLike(titulo));
        }

        if (nomeAutor != null) {
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        if (genero != null) {
            specs = specs.and(generoEqual(genero));
        }

        if (anoPublicacao != null) {
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }

        return livroRepository.findAll(specs);

    }

    public void atualizar(Livro livro) {
        if (livro.getId() == null) {
            throw new IllegalArgumentException("Para atualizar é necessário que o livro esteja salvo na base.");
        }

        livroValidator.validar(livro);
        livroRepository.save(livro);
    }
}
