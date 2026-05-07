package br.com.murilo.libraryapi.controller.mappers;

import br.com.murilo.libraryapi.controller.dto.CadastroLivroDto;
import br.com.murilo.libraryapi.controller.dto.ResultadoPesquisaLivroDto;
import br.com.murilo.libraryapi.model.Livro;
import br.com.murilo.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDto dto);

    public abstract ResultadoPesquisaLivroDto toDto(Livro livro);


}
