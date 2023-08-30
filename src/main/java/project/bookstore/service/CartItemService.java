package project.bookstore.service;

import project.bookstore.model.CartItem;

public interface CartItemService {
    CartItem save(CartItem cartItem);

    CartItem findById(Long id);

    CartItem update(Long id, int quantity);

    void delete(Long id);
}
