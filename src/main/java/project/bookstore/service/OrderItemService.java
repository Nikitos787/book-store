package project.bookstore.service;

import java.util.List;
import project.bookstore.dto.OrderItemResponseDto;
import project.bookstore.model.OrderItem;

public interface OrderItemService {
    OrderItem save(OrderItem orderItem);

    List<OrderItemResponseDto> findByOrderId(Long id, Long userId);

    OrderItemResponseDto findParticularOrderItemByOrderId(Long orderId,
                                                          Long orderItemId,
                                                          Long userId);
}
