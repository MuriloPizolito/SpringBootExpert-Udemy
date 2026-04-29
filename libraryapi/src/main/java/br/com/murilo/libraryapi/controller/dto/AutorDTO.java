package br.com.murilo.libraryapi.controller.dto;

import br.com.murilo.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

// DTO - data transfer object
// passa dados para a camada de domínio
public record AutorDTO(

        UUID id,

        @NotBlank(message = "campo obrigatório")// parq strings
        @Size(min = 2, max = 100, message = "campo fora do tamanho padrão")
        String nome,

        @NotNull(message = "campo obrigatório") // campos que não podem vir nulos, usado mais pora números
        @Past(message = "não pode ser uma data futura")
        LocalDate dataNascimento,

        @NotBlank(message = "campo obrigatório")
        @Size(min = 1, max = 50, message = "campo fora do tamanho padrão")
        String nacionalidade) {

    public Autor mapearParaAutor() {
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }

}
