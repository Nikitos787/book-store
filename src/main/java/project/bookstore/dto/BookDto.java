package project.bookstore.dto;

import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
    private String description;
    private String coverImage;
    private Set<Long> categoriesIds;
}
