package project.bookstore.service;

import project.bookstore.dto.CartItemRequestDto;
import project.bookstore.dto.ShoppingCartResponseDto;
import project.bookstore.model.ShoppingCart;
import project.bookstore.model.User;

public interface ShoppingCartService {

    ShoppingCart save(User user);

    ShoppingCartResponseDto findByUser(User user);

    ShoppingCartResponseDto addCartItemToShoppingCart(
            CartItemRequestDto cartItemRequestDto);

    ShoppingCartResponseDto removeBookFromShoppingCart(Long cartItemId, User user);

    ShoppingCart getModelById(Long id);

    void clearShoppingCart(User user);
}
