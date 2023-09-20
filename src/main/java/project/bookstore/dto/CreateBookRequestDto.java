package project.bookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @NotBlank(message = "title can't be null or empty")
    private String title;
    @NotBlank(message = "author can't be null or empty")
    private String author;
    @NotNull(message = "price can't be null")
    @Min(value = 0, message = "price should be greater than 0")
    private BigDecimal price;
    @NotBlank(message = "description can't be null or empty")
    private String description;
    @NotBlank(message = "coverImage can't be null or empty")
    private String coverImage;
}
