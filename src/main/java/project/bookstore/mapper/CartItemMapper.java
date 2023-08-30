package project.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.bookstore.config.MapperConfig;
import project.bookstore.dto.CartItemDto;
import project.bookstore.dto.CartItemRequestDto;
import project.bookstore.model.CartItem;

@Mapper(config = MapperConfig.class, uses = BookMapper.class)
public interface CartItemMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    @Mapping(source = "quantity", target = "quantity")
    CartItemDto toDto(CartItem cartItem);

    @Mapping(ignore = true, target = "id")
    @Mapping(source = "bookId", target = "book.id")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "shoppingCartId", target = "shoppingCart.id")
    @Mapping(ignore = true, target = "deleted")
    CartItem toModel(CartItemRequestDto cartItemRequestDto);
}
