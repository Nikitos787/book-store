package project.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderRequestDto {
    @NotBlank(message = "shippingAddress can't be empty or null")
    private String shippingAddress;
}
