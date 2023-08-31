package project.bookstore.service;

import java.util.List;
import project.bookstore.dto.OrderResponseDto;
import project.bookstore.dto.StatusDto;
import project.bookstore.model.Order;
import project.bookstore.model.User;

public interface OrderService {
    OrderResponseDto save(User user);

    Order findById(Long id);

    List<OrderResponseDto> findAllOrderHistoryByUser(User user);

    OrderResponseDto changeStatus(Long id, StatusDto statusdto);
}
