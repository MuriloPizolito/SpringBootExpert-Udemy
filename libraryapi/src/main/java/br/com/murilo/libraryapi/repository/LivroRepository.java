package br.com.murilo.libraryapi.repository;

import br.com.murilo.libraryapi.model.Autor;
import br.com.murilo.libraryapi.model.GeneroLivro;
import br.com.murilo.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */

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

    //JPQL -> referencia as entidades e as propriedades
    @Query(" select l from Livro as l order by l.titulo, l.preco ")
    //usa o nome das entidades
    // select l.* from livro as l order by l.titulo, l.preco
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    //select a.* from livro l join autor a on a.id = l.id_autor
    @Query("select a from Livro l join l.autor a ")
    List<Autor> listarAutoresDosLivros();

    // select distinct l.* from livro l
    @Query("select distinct l.titulo from Livro l ")
    List<String> listarNomesDiferentesLivros();

    // caso for uma query maior
    @Query("""
            select l.genero
            from Livro l
            join l.autor a 
            where a.nacionalidade = 'Russa'
            order by l.genero
            """)
    List<String> listarGenerosAutoresBrasileiros();

    // Named Parameters -> parâmetros nomeados
    @Query(" select l from Livro l where l.genero = :nomeParametro order by :paramOrdenacao")
    List<Livro> findByGeneroNamedParameters(
            @Param("nomeParametro") GeneroLivro generoLivro,
            @Param("paramOrdenacao") String nomePropriedade);

    // Positional Parameters -> vc passa a posição do parâmetro
    @Query(" select l from Livro l where l.genero = ?1 order by ?2 ")
    List<Livro> findByGeneroPositionalParameters(GeneroLivro generoLivro, String nomePropriedade);

    // delete e insert com @Query
    @Modifying // avisa que vai modificar algum registro e não só buscar
    @Transactional // precisa abrir uma transação "janela"
    @Query(" delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro genero);

    @Modifying
    @Transactional
    @Query(" update Livro set dataPublicacao = ?1 ") //sem where só para testar mesmo
    void updateDataPublicacao(LocalDate novcaData);

}
