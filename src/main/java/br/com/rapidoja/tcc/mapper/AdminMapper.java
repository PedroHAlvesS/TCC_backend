package br.com.rapidoja.tcc.mapper;

import br.com.rapidoja.tcc.dto.admin.AdminRequestDTO;
import br.com.rapidoja.tcc.dto.admin.AdminResponseDTO;
import br.com.rapidoja.tcc.model.User;
import br.com.rapidoja.tcc.util.Formatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = Formatter.class
)
public interface AdminMapper {

    @Mapping(target = "profile", source = "profile", qualifiedByName = "formatProfile")
    AdminResponseDTO toResponseDTO(User user);

    User toEntity(AdminRequestDTO adminRequestDTO);

}
