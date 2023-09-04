package project.bookstore.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.bookstore.dto.OrderItemResponseDto;
import project.bookstore.mapper.OrderItemMapper;
import project.bookstore.model.OrderItem;
import project.bookstore.repository.OrderItemRepository;
import project.bookstore.service.OrderItemService;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItemResponseDto> findByOrderId(Long orderId, Long userId) {
        return orderItemRepository.findAllByOrderId(orderId, userId).stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemResponseDto findParticularOrderItemByOrderId(Long orderId,
                                                                 Long orderItemId,
                                                                 Long userId) {
        return findByOrderId(orderId, userId).stream()
                .filter(orderItem -> orderItem.getId().equals(orderItemId))
                .findFirst()
                .orElseThrow();
    }
}
