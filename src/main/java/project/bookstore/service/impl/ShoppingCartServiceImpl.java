package project.bookstore.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.bookstore.dto.CartItemRequestDto;
import project.bookstore.dto.ShoppingCartResponseDto;
import project.bookstore.exception.EntityNotFoundException;
import project.bookstore.mapper.CartItemMapper;
import project.bookstore.mapper.ShoppingCartMapper;
import project.bookstore.model.CartItem;
import project.bookstore.model.ShoppingCart;
import project.bookstore.model.User;
import project.bookstore.repository.ShoppingCartRepository;
import project.bookstore.service.CartItemService;
import project.bookstore.service.ShoppingCartService;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemService cartItemService;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCart save(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto findByUser(User user) {
        return shoppingCartMapper.toDto(getModelById(user.getId()));
    }

    @Transactional
    @Override
    public ShoppingCartResponseDto addCartItemToShoppingCart(
            CartItemRequestDto cartItemRequestDto) {
        CartItem cartItem = cartItemMapper.toModel(cartItemRequestDto);
        ShoppingCart shoppingCart = getModelById(cartItemRequestDto.getShoppingCartId());
        shoppingCart.getCartItems().add(cartItem);
        cartItemService.save(cartItem);
        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public ShoppingCartResponseDto removeBookFromShoppingCart(Long cartItemId, User user) {
        cartItemService.delete(cartItemId);
        return findByUser(user);
    }

    @Override
    public ShoppingCart getModelById(Long id) {
        return shoppingCartRepository.findByUserId(id).orElseThrow(() ->
                new EntityNotFoundException(String.format(
                        "Can't find shop cart by user with id: %s", id)));
    }

    @Override
    public void clearShoppingCart(User user) {
        ShoppingCart shoppingCart = getModelById(user.getId());
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            cartItemService.delete(cartItem.getId());
        }
        shoppingCart.setCartItems(null);
        shoppingCartRepository.save(shoppingCart);
    }
}
