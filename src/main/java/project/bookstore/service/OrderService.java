package project.bookstore.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import project.bookstore.dto.OrderItemResponseDto;
import project.bookstore.dto.OrderResponseDto;
import project.bookstore.dto.StatusDto;
import project.bookstore.model.Order;
import project.bookstore.model.OrderItem;

public interface OrderService {
    OrderResponseDto changeOrderStatus(Long id, StatusDto statusdto);

    @Transactional
    OrderResponseDto saveOrder(Long userId);

    Order findOrderById(Long id);

    List<OrderResponseDto> findAllOrdersHistoryByUser(Long userId);

    OrderItem saveOrderItem(OrderItem orderItem);

    List<OrderItemResponseDto> findOrderItemsByOrderId(Long orderId, Long userId);

    OrderItemResponseDto findParticularOrderItemByOrderId(Long orderId,
                                                          Long orderItemId,
                                                          Long userId);
}
