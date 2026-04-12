package br.com.murilo.libraryapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryapiApplication {

    public static void main(String[] args) {
//		var context =
        SpringApplication.run(LibraryapiApplication.class, args);
//        AutorRepository repository = context.getBean(AutorRepository.class);

//        exemploSalvarRegistro(repository);
    }

// ao invés de fazer aqui, faz diretamente na classe de teste
//    public static void exemploSalvarRegistro(AutorRepository autorRepository){
//        Autor autor = new Autor();
//        autor.setNome("José");
//        autor.setNacionalidade("Brasileira");
//        autor.setDataNascimento(LocalDate.of(1950, 1, 31));
//
//        var autorSalvo = autorRepository.save(autor);
//
//        System.out.println("Autor salvo: " + autorSalvo);
//    }

}
