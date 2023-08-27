package project.bookstore.repository.book.specification;

import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import project.bookstore.model.Book;
import project.bookstore.repository.SpecificationProvider;

@Component
public class PriceSpecificationProvider implements SpecificationProvider<Book> {
    private static final String JOIN_TABLE = "categories";
    private static final String PRICE_KEY_FIELD = "price";

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> {
            root.fetch(JOIN_TABLE);
            return root.get(PRICE_KEY_FIELD).in(Arrays.stream(params).toArray());

        };
    }

    @Override
    public String getKey() {
        return PRICE_KEY_FIELD;
    }
}
