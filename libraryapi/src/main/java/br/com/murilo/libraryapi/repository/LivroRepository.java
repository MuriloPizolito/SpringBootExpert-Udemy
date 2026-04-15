package br.com.murilo.libraryapi.repository;

import br.com.murilo.libraryapi.model.Autor;
import br.com.murilo.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query Method

    List<Livro> findByAutor(Autor autor); // select * from livro where id_autor = id

}
