package project.bookstore.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.bookstore.dto.CartItemRequestDto;
import project.bookstore.dto.ShoppingCartResponseDto;
import project.bookstore.dto.ShoppingCartUpdateQuantityRequestDto;
import project.bookstore.exception.EntityNotFoundException;
import project.bookstore.exception.OperationException;
import project.bookstore.mapper.CartItemMapper;
import project.bookstore.mapper.ShoppingCartMapper;
import project.bookstore.model.CartItem;
import project.bookstore.model.ShoppingCart;
import project.bookstore.model.User;
import project.bookstore.repository.CartItemRepository;
import project.bookstore.repository.ShoppingCartRepository;
import project.bookstore.repository.UserRepository;
import project.bookstore.service.ShoppingCartService;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final UserRepository userRepository;

    @Override
    public ShoppingCart save(Long userId) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(getUserFromDb(userId));
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto findByUser(Long userId) {
        return shoppingCartMapper.toDto(getModelById(userId));
    }

    @Transactional
    @Override
    public ShoppingCartResponseDto addCartItemToShoppingCart(
            CartItemRequestDto cartItemRequestDto) {
        CartItem cartItem = cartItemMapper.toModel(cartItemRequestDto);
        ShoppingCart shoppingCart = getModelById(cartItemRequestDto.getShoppingCartId());
        shoppingCart.getCartItems().add(cartItem);
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public ShoppingCartResponseDto removeBookFromShoppingCart(Long cartItemId, Long userId) {
        if (isSuitableShoppingCart(cartItemId, userId)) {
            cartItemRepository.deleteById(cartItemId);
            return findByUser(userId);
        }
        throw new OperationException(
                String.format(
                        "It's impossible to remove book not from your own shopping cart. "
                                + "This cart item with id: %s is situated in another shopping cart",
                        cartItemId));
    }

    @Override
    public void delete(Long id) {
        shoppingCartRepository.deleteById(id);
    }

    @Override
    public void clearShoppingCart(Long shoppingCartId) {
        ShoppingCart shoppingCart = getModelById(shoppingCartId);
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            cartItemRepository.deleteById(cartItem.getId());
        }
        shoppingCart.setCartItems(null);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart getModelById(Long id) {
        return shoppingCartRepository.findByUserId(id).orElseThrow(() ->
                new EntityNotFoundException(String.format(
                        "Can't find shop cart by user with id: %s", id)));
    }

    @Override
    public void updateCartItemQuantity(Long cartItemId,
                                       ShoppingCartUpdateQuantityRequestDto quantityRequestDto,
                                       Long userId) {
        if (isSuitableShoppingCart(cartItemId, userId)) {
            CartItem cartItem = getCartItemByIdFromDb(cartItemId);
            cartItem.setQuantity(quantityRequestDto.getQuantity());
            cartItemRepository.save(cartItem);
        }
        throw new OperationException(
                String.format(
                        "It's impossible to update quantity of cart item "
                                + "if this cart item with id: %s is belong to another (not yours)"
                                + " shopping cart", cartItemId));
    }

    private CartItem getCartItemByIdFromDb(Long cartItemId) {
        return cartItemRepository.findById(cartItemId).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Can't find cart item by id: %s", cartItemId)));
    }

    private User getUserFromDb(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Can't find user by user id: %s", userId)));
    }

    private boolean isSuitableShoppingCart(Long cartItemId, Long userId) {
        return getCartItemByIdFromDb(cartItemId).getShoppingCart().getId().equals(userId);
    }
}
