package project.bookstore.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import project.bookstore.model.Order;

@Data
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private Set<OrderItemResponseDto> orderItemDtos;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private Order.Status status;

}
