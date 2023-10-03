package project.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.bookstore.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
