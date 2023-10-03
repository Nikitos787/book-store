package project.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.bookstore.config.MapperConfig;
import project.bookstore.dto.OrderItemResponseDto;
import project.bookstore.model.OrderItem;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "quantity", target = "quantity")
    OrderItemResponseDto toDto(OrderItem orderItem);
}
