package project.bookstore.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.bookstore.model.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @Query("FROM ShoppingCart sh "
            + "LEFT JOIN FETCH sh.user u "
            + "LEFT JOIN FETCH sh.cartItems ca "
            + "LEFT JOIN FETCH ca.book b "
            + "LEFT JOIN FETCH b.categories "
            + "LEFT JOIN FETCH u.roles WHERE u.id = :userId")
    Optional<ShoppingCart> findByUserId(@Param("userId") Long userId);
}
