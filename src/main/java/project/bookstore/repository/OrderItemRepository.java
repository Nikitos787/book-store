package project.bookstore.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.bookstore.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("FROM OrderItem oi "
            + "LEFT JOIN FETCH oi.order o "
            + "LEFT JOIN FETCH o.user u "
            + "LEFT JOIN FETCH u.roles r "
            + "LEFT JOIN FETCH oi.book b "
            + "LEFT JOIN FETCH b.categories cat "
            + "WHERE o.id = :id AND u.id = :userId")
    List<OrderItem> findAllByOrderId(@Param("id") Long id, @Param("userId") Long userId);
}
