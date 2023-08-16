package mate.academy.bookstore.dto;

import lombok.Data;

@Data
public class BookSearchParametersDto {
    private String[] titles;
    private String[] authors;
    private String[] prices;
}
