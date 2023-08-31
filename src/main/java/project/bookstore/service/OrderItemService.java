package project.bookstore.service;

import java.util.List;
import project.bookstore.dto.OrderItemResponseDto;
import project.bookstore.model.OrderItem;
import project.bookstore.model.User;

public interface OrderItemService {
    OrderItem save(OrderItem orderItem);

    List<OrderItemResponseDto> findByOrderId(Long id, User user);

    OrderItemResponseDto findParticularOrderItemByOrderId(Long orderId,
                                                          Long orderItemId,
                                                          User user);
}
