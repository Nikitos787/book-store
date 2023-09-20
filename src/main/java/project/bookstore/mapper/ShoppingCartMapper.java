package project.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.bookstore.config.MapperConfig;
import project.bookstore.dto.ShoppingCartResponseDto;
import project.bookstore.model.ShoppingCart;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "cartItems", target = "cartItemDtos")
    ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);
}
