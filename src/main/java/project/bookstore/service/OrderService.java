package project.bookstore.service;

import java.util.List;
import project.bookstore.dto.OrderResponseDto;
import project.bookstore.dto.StatusDto;
import project.bookstore.model.Order;

public interface OrderService {
    OrderResponseDto save(Long userId);

    Order findById(Long id);

    List<OrderResponseDto> findAllOrderHistoryByUser(Long userId);

    OrderResponseDto changeStatus(Long id, StatusDto statusdto);
}
