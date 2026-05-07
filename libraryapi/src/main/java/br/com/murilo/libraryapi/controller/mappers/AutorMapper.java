package br.com.murilo.libraryapi.controller.mappers;

import br.com.murilo.libraryapi.controller.dto.AutorDTO;
import br.com.murilo.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    // @Mapping(source = "nome", target = "nomeAutor") quando os nomes das propriedades são diferentes
    @Mapping(source = "nome" ,target = "nome")
    @Mapping(source = "dataNascimento" ,target = "dataNascimento")
    @Mapping(source = "nacionalidade" ,target = "nacionalidade") // nao precisa da anotação quando o nome é igual, apenas de exemplo
    Autor toEntity(AutorDTO autorDTO);

    AutorDTO toDto(Autor autor);

}
