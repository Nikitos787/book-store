package project.bookstore.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.bookstore.dto.OrderItemResponseDto;
import project.bookstore.dto.OrderResponseDto;
import project.bookstore.dto.StatusDto;
import project.bookstore.exception.EntityNotFoundException;
import project.bookstore.mapper.OrderItemMapper;
import project.bookstore.mapper.OrderMapper;
import project.bookstore.model.CartItem;
import project.bookstore.model.Order;
import project.bookstore.model.OrderItem;
import project.bookstore.model.ShoppingCart;
import project.bookstore.model.User;
import project.bookstore.repository.OrderItemRepository;
import project.bookstore.repository.OrderRepository;
import project.bookstore.service.OrderService;
import project.bookstore.service.ShoppingCartService;
import project.bookstore.service.UserService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartService shoppingCartService;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final UserService userService;

    @Transactional
    @Override
    public OrderResponseDto saveOrder(Long userId, String shippingAddress) {
        Order savedOrderWithoutItems = createInitialOrderInDb(userId, shippingAddress);

        ShoppingCart shoppingCart = shoppingCartService.getModelById(userId);

        Set<OrderItem> orderItemsForOrder =
                createOrderItemsForOrder(savedOrderWithoutItems, shoppingCart);
        savedOrderWithoutItems.setOrderItems(orderItemsForOrder);

        savedOrderWithoutItems.setTotal(calculateTotal(orderItemsForOrder));
        shoppingCartService.clearShoppingCart(userId);
        return orderMapper.toDto(orderRepository.save(savedOrderWithoutItems));
    }

    @Override
    public List<OrderResponseDto> findAllOrdersHistoryByUser(Long userId, Pageable pageable) {
        return orderRepository.findByUser(userId, pageable).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderItemResponseDto> findOrderItemsByOrderId(Long orderId,
                                                              Long userId,
                                                              Pageable pageable) {
        Order order = findOrderById(orderId);
        if (order.getUser().getId().equals(userId)) {
            return orderItemRepository.findAllByOrderId(orderId, userId, pageable).stream()
                    .map(orderItemMapper::toDto)
                    .toList();
        }
        throw new EntityNotFoundException(
                String.format(
                        "Can't find list of order items for such order %s. "
                                + "Is it order belongs to user with id: %s", orderId, userId));
    }

    @Override
    public OrderItemResponseDto findParticularOrderItemByOrderId(Long orderId,
                                                                 Long orderItemId,
                                                                 Long userId) {
        return orderItemMapper.toDto(
                orderItemRepository.findOrderItemsByOrderId(orderId, userId, orderItemId)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        String.format(
                                                "Can't find order item from db with id: %s. "
                                                        + "Does this order item belong "
                                                        + "to user with id: %s",
                                                orderItemId, userId))));
    }

    @Override
    public OrderResponseDto changeOrderStatus(Long id, StatusDto statusdto) {
        Order orderFromDb = findOrderById(id);
        orderFromDb.setStatus(statusdto.getStatus());
        return orderMapper.toDto(orderRepository.save(orderFromDb));
    }

    @Override
    public Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Can't find order from db with id: %s", id)
                ));
    }

    private Order createInitialOrderInDb(Long userId, String shippingAddress) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.Status.PENDING);
        User user = userService.findById(userId);
        order.setUser(user);
        order.setShippingAddress(shippingAddress);
        order.setTotal(BigDecimal.ZERO);
        return orderRepository.save(order);
    }

    private Set<OrderItem> createOrderItemsForOrder(Order order, ShoppingCart shoppingCart) {
        Set<OrderItem> orderItemSet = new HashSet<>();
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getBook().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            orderItemSet.add(orderItemRepository.save(orderItem));
        }
        return orderItemSet;
    }

    private BigDecimal calculateTotal(Set<OrderItem> orderItemSet) {
        return orderItemSet.stream().map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
