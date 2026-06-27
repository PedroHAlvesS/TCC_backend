package br.com.rapidoja.tcc.mapper;

import br.com.rapidoja.tcc.dto.customer.CustomerRequestDTO;
import br.com.rapidoja.tcc.dto.customer.CustomerResponseDTO;
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
public interface CustomerMapper {

    @Mapping(target = "profile", source = "profile", qualifiedByName = "formatProfile")
    CustomerResponseDTO toResponseDTO(User user);

    User toEntity(CustomerRequestDTO customerRequestDTO);

}
