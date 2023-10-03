package project.bookstore.dto;

import lombok.Data;
import project.bookstore.model.Order;

@Data
public class StatusDto {
    private Order.Status status;
}
