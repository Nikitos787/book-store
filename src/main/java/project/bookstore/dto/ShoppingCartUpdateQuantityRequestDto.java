package project.bookstore.dto;

import lombok.Data;

@Data
public class ShoppingCartUpdateQuantityRequestDto {
    private Long id;
    private int quantity;
}
