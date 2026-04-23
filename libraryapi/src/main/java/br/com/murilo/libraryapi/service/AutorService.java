package br.com.murilo.libraryapi.service;

import br.com.murilo.libraryapi.model.Autor;
import br.com.murilo.libraryapi.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public Autor salvar(Autor autor){

        return autorRepository.save(autor);
    }

}
