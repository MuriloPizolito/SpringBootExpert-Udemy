package br.com.murilo.libraryapi.repository;

import br.com.murilo.libraryapi.model.Autor;
import br.com.murilo.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query Methods

    List<Livro> findByAutor(Autor autor); // select * from livro where id_autor = id

    //select * from livro where titulo = ?
    List<Livro> findByTitulo(String titulo); // busca pelo título do livro

    //select * from livro where isbn = ?
    List<Livro> findByIsbn(String isbn); // busca pelo código isbn do livro

    // select * from livro where titulo = ? and preco = ?
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco); // AND. É possível usar mais de um campo para busca

    // select * from livro where titulo = ? or isbn = ?
    List<Livro> findByTituloOrIsbn(String titulo, String isbn); // OR

    List<Livro> findByTituloOrIsbnOrderByTitulo(String titulo, String isbn); // OR e OrderBy

    // select * from livro where data_publicacao between ? and ?
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim); //busca livros entre as datas passadas

}
