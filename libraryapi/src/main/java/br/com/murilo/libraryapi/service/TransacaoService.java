package br.com.murilo.libraryapi.service;

import br.com.murilo.libraryapi.model.Autor;
import br.com.murilo.libraryapi.model.GeneroLivro;
import br.com.murilo.libraryapi.model.Livro;
import br.com.murilo.libraryapi.repository.AutorRepository;
import br.com.murilo.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    // método tem quer ser public
    @Transactional  // abre uma transação, e no final faz o commit ou rollback
    public void executar() {
        // salva o autor
        Autor autor = new Autor();
        autor.setNome("Teste do Francisco");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1952, 1, 31));

        autorRepository.save(autor); // só executa no final da transação
//        autorRepository.saveAndFlush(autor); ele manda pro banco antes, mas ainda da rollback se tiver erro

        // salva o livro
        Livro livro = new Livro();
        livro.setIsbn("98886-84746");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Teste Livro do Francisco");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        livro.setAutor(autor);

        livroRepository.save(livro); // só executa no final da transação
//        livroRepository.saveAndFlush(livro); ele manda pro banco antes, mas ainda da rollback se tiver erro

        if (autor.getNome().equals("Teste do Francisco")) {
            throw new RuntimeException("Rollback!");
        }
    }

    @Transactional
    public void atualizacaoSemAtualizar() {
        var livro = livroRepository
                .findById(UUID.fromString("5f0e567c-a0f2-4c26-b6ca-4e499539d7fa"))
                .orElse(null);

        livro.setDataPublicacao(LocalDate.of(2024, 6, 1));

//        livroRepository.save(livro); nao tem a necessidade de fazer isso, pq está com uma transação aberta
    }

    //exemplo de quando usar o cenário exemplificado acima
    // livro (titulo, ...., nome_arquivo))
    @Transactional
    public void salvarLivroComFoto() {
        // salva o livro
        // repository.save(livro);

        // pega o id do livro = livro.getId();
        // var id = livro.getId();

        // salvar foto do livro -> bucket na nuvem
        // bucketService.salvar(livro.getFoto(), id + ".png");

        // atualizar o nome do arquivo salvo
        // livro.setNomeArquivoFoto(id + ".png"); aqui não precisaria chamar o repository novamente.
    }

}
