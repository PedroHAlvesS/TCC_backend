package br.com.rapidoja.tcc.mapper;

import br.com.rapidoja.tcc.dto.order.OrderRequestDTO;
import br.com.rapidoja.tcc.dto.order.OrderResponseDTO;
import br.com.rapidoja.tcc.model.Order;
import br.com.rapidoja.tcc.util.Formatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = Formatter.class
)
public interface OrderMapper {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.name", target = "customerName")
    @Mapping(source = "deliveryMan.id", target = "deliveryManId")
    @Mapping(source = "deliveryMan.name", target = "deliveryManName")
    @Mapping(source = "address.street", target = "address.street")
    @Mapping(source = "address.number", target = "address.number")
    @Mapping(source = "address.neighborhood", target = "address.neighborhood")
    @Mapping(source = "address.zipCode", target = "address.zipCode")
    @Mapping(source = "address.complement", target = "address.complement")
    @Mapping(source = "status", target = "status", qualifiedByName = "formatOrderStatus")
    OrderResponseDTO toResponseDTO(Order order);

    Order toEntity(OrderRequestDTO orderRequestDTO);
}
