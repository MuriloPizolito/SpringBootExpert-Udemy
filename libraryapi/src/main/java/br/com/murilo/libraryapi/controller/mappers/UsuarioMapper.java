package br.com.murilo.libraryapi.controller.mappers;

import br.com.murilo.libraryapi.controller.dto.UsuarioDto;
import br.com.murilo.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDto dto);

}
