package br.com.rapidoja.tcc.mapper;

import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManRequestDTO;
import br.com.rapidoja.tcc.dto.deliveryman.DeliveryManResponseDTO;
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
public interface DeliveryManMapper {

    @Mapping(target = "profile", source = "profile", qualifiedByName = "formatProfile")
    DeliveryManResponseDTO toResponseDTO(User user);

    User toEntity(DeliveryManRequestDTO deliveryManRequestDTO);

}
