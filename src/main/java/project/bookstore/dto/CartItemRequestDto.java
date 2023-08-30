package project.bookstore.dto;

import lombok.Data;

@Data
public class CartItemRequestDto {
    private Long bookId;
    private int quantity;
    private Long shoppingCartId;
}
