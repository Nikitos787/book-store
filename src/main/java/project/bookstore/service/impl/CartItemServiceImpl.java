package project.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.bookstore.exception.EntityNotFoundException;
import project.bookstore.model.CartItem;
import project.bookstore.repository.CartItemRepository;
import project.bookstore.service.CartItemService;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository carItemRepository;

    @Override
    public CartItem save(CartItem cartItem) {
        return carItemRepository.save(cartItem);
    }

    @Override
    public CartItem findById(Long id) {
        return carItemRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Can't find cartItem in db by id: %s", id)));
    }

    @Override
    public CartItem update(Long id, int quantity) {
        CartItem cartItem = findById(id);
        cartItem.setQuantity(quantity);
        return save(cartItem);
    }

    @Override
    public void delete(Long id) {
        carItemRepository.deleteById(id);
    }
}
