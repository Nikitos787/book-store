package project.bookstore.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import project.bookstore.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @NonNull
    @Query("FROM Order o "
            + "LEFT JOIN FETCH o.orderItems oi "
            + "LEFT JOIN FETCH oi.book b "
            + "WHERE o.id = :id")
    Optional<Order> findById(@Param("id") @NonNull Long id);

    @Query("FROM Order o "
            + "LEFT JOIN FETCH o.user u "
            + "LEFT JOIN FETCH u.roles r "
            + "LEFT JOIN FETCH o.orderItems oi "
            + "LEFT JOIN FETCH oi.book "
            + "WHERE u.id = :id")
    List<Order> findByUser(@Param("id") Long userId);
}
