package project.bookstore.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import project.bookstore.dto.OrderItemResponseDto;
import project.bookstore.dto.OrderResponseDto;
import project.bookstore.dto.StatusDto;
import project.bookstore.model.Order;

public interface OrderService {
    OrderResponseDto changeOrderStatus(Long id, StatusDto statusdto);

    @Transactional
    OrderResponseDto saveOrder(Long userId, String shippingAddress);

    Order findOrderById(Long id);

    List<OrderResponseDto> findAllOrdersHistoryByUser(Long userId, Pageable pageable);

    List<OrderItemResponseDto> findOrderItemsByOrderId(Long orderId,
                                                       Long userId,
                                                       Pageable pageable);

    OrderItemResponseDto findParticularOrderItemByOrderId(Long orderId,
                                                          Long orderItemId,
                                                          Long userId);
}
