package br.com.rapidoja.tcc.mapper;

import br.com.rapidoja.tcc.dto.CustomerRequestDTO;
import br.com.rapidoja.tcc.dto.CustomerResponseDTO;
import br.com.rapidoja.tcc.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerMapper {

    CustomerResponseDTO toResponseDTO(User user);

    User toEntity(CustomerRequestDTO customerRequestDTO);

}
