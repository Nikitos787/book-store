package project.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.bookstore.config.MapperConfig;
import project.bookstore.dto.OrderResponseDto;
import project.bookstore.model.Order;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "orderItems", target = "orderItemDtos")
    @Mapping(source = "orderDate", target = "orderDate")
    @Mapping(source = "total", target = "total")
    @Mapping(source = "status", target = "status")
    OrderResponseDto toDto(Order order);
}
