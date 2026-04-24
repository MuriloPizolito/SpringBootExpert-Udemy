package br.com.murilo.libraryapi.controller;

import br.com.murilo.libraryapi.model.Autor;

import java.time.LocalDate;
import java.util.UUID;

// DTO - data transfer object
public record AutorDTO(UUID id,
                       String nome,
                       LocalDate dataNascimento,
                       String nacionalidade) {

    public Autor mapearParaAutor() {
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }

}
