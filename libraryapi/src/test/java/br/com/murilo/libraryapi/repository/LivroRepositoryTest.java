package br.com.murilo.libraryapi.repository;

import br.com.murilo.libraryapi.model.Autor;
import br.com.murilo.libraryapi.model.GeneroLivro;
import br.com.murilo.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    // na classe de teste não é obrigatório utilizar os modificadores de acesso

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("98886-84746");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = autorRepository
                .findById(UUID.fromString("aeb8cf56-bf10-4fd2-a9ea-0c2a192877be"))
                .orElse(null);

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    // opção padrão de implementação
    @Test
    void salvarAutorELivroTest() {
        Livro livro = new Livro();
        livro.setIsbn("98886-84746");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Terceiro Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("José");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1952, 1, 31));

        autorRepository.save(autor);

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    //salvando em cascata, não salvei o autor no banco pelo código, mas ele faz isso automaticamente antes de solvar o livro, por conta do Cascade
    @Test
    void salvarCascadeTest() {
        Livro livro = new Livro();
        livro.setIsbn("98886-84746");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1952, 1, 31));

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void atualizarAutorDoLivro() {
        UUID id = UUID.fromString("60a294a7-4da7-4540-bc69-efcf19172daa");
        var livroParaAtualizar = livroRepository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("aeb8cf56-bf10-4fd2-a9ea-0c2a192877be");
        Autor maria = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(maria);

        livroRepository.save(livroParaAtualizar);
    }

    @Test
    void deletar() {
        UUID id = UUID.fromString("60a294a7-4da7-4540-bc69-efcf19172daa");
        livroRepository.deleteById(id);
    }

    // Usar com cuidado, ele deleta o autor junto por conta da operação em cascata. Poderia acontecer desse autor ser dono de outros livros, isso geraria um problema
    @Test
    void deletarCascade() {
        UUID id = UUID.fromString("20c8dee5-ebab-4cd6-8a47-ef1385d3d4e6");
        livroRepository.deleteById(id);
    }


}