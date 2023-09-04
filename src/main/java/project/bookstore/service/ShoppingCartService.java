package project.bookstore.service;

import project.bookstore.dto.CartItemRequestDto;
import project.bookstore.dto.ShoppingCartResponseDto;
import project.bookstore.dto.ShoppingCartUpdateQuantityRequestDto;
import project.bookstore.model.ShoppingCart;

public interface ShoppingCartService {

    ShoppingCart save(Long userId);

    ShoppingCartResponseDto findByUser(Long userId);

    ShoppingCartResponseDto addCartItemToShoppingCart(
            CartItemRequestDto cartItemRequestDto);

    ShoppingCartResponseDto removeBookFromShoppingCart(Long cartItemId, Long userId);

    void delete(Long id);

    void clearShoppingCart(Long shoppingCartId);

    ShoppingCart getModelById(Long id);

    void updateCartItemQuantity(Long cartItemId,
                                ShoppingCartUpdateQuantityRequestDto quantityRequestDto);
}
