package project.bookstore.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.bookstore.dto.OrderResponseDto;
import project.bookstore.dto.StatusDto;
import project.bookstore.exception.EntityNotFoundException;
import project.bookstore.mapper.OrderMapper;
import project.bookstore.model.CartItem;
import project.bookstore.model.Order;
import project.bookstore.model.OrderItem;
import project.bookstore.model.ShoppingCart;
import project.bookstore.model.User;
import project.bookstore.repository.OrderRepository;
import project.bookstore.repository.UserRepository;
import project.bookstore.service.OrderItemService;
import project.bookstore.service.OrderService;
import project.bookstore.service.ShoppingCartService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartService shoppingCartService;
    private final OrderItemService orderItemService;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;

    @Override
    public OrderResponseDto changeStatus(Long id, StatusDto statusdto) {
        Order orderFromDb = findById(id);
        orderFromDb.setStatus(statusdto.getStatus());
        return orderMapper.toDto(orderRepository.save(orderFromDb));
    }

    @Transactional
    @Override
    public OrderResponseDto save(Long userId) {
        Order savedOrderWithoutItems = createInitialOrderInDb(userId);

        ShoppingCart shoppingCart = shoppingCartService.getModelById(userId);

        Set<OrderItem> orderItemsForOrder =
                createOrderItemsForOrder(savedOrderWithoutItems, shoppingCart);
        savedOrderWithoutItems.setOrderItems(orderItemsForOrder);

        savedOrderWithoutItems.setTotal(calculateTotal(orderItemsForOrder));
        shoppingCartService.clearShoppingCart(userId);
        return orderMapper.toDto(orderRepository.save(savedOrderWithoutItems));
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Can't find order from db with id: %s", id)
                ));
    }

    @Override
    public List<OrderResponseDto> findAllOrderHistoryByUser(Long userId) {
        return orderRepository.findByUser(userId).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    private Order createInitialOrderInDb(Long userId) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.Status.PENDING);
        User user = getUserFromDb(userId);
        order.setUser(user);
        order.setShippingAddress(user.getShippingAddress());
        order.setTotal(BigDecimal.ZERO);
        return orderRepository.save(order);
    }

    private User getUserFromDb(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Can't find user from db with id: %s", userId)));
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
            orderItemSet.add(orderItemService.save(orderItem));
        }
        return orderItemSet;
    }

    private BigDecimal calculateTotal(Set<OrderItem> orderItemSet) {
        return orderItemSet.stream().map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
