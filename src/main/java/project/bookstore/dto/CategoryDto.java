package project.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDto {
    private Long id;
    @NotBlank(message = "name can't be null or empty")
    private String name;
    private String description;
}
