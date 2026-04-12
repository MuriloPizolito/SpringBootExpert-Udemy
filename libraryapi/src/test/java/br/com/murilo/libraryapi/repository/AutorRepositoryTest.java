package br.com.murilo.libraryapi.repository;

import br.com.murilo.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest // anotação para subir a aplicação de test
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Russa");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));

        var autorSalvo = autorRepository.save(autor);

        System.out.println("Autor salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest() {
        var id = UUID.fromString("4c75457f-2c38-44cc-b01a-f7eb4062ae71");// id do autor copiado do banco

        Optional<Autor> possivelAutor = autorRepository.findById(id);

        if (possivelAutor.isPresent()) {
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do autor: ");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1960, 1, 31));

            autorRepository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest() {
        List<Autor> lista = autorRepository.findAll();
        lista.forEach(System.out::println); // for each percorrendo a lista e imprimindo
    }

    @Test
    public void countTest() {
        System.out.println("Contagem de autores: " + autorRepository.count());
    }

    @Test
    public void deletePorIdTest() {
        var id = UUID.fromString("4c75457f-2c38-44cc-b01a-f7eb4062ae71"); // id do autor copiado do banco
        autorRepository.deleteById(id);
    }

    @Test
    public void deletePorObjeto() {
        var id = UUID.fromString("996f618f-23f8-47a6-8e91-e76b6e8d2cd8"); // id do autor copiado do banco
        var maria = autorRepository.findById(id).get(); // usando o get, pq eu sei que esse autor existe
        autorRepository.delete(maria);
    }


}
