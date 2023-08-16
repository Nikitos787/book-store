package mate.academy.bookstore.repository.book.specification;

import java.util.Arrays;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PriceSpecificationProvider implements SpecificationProvider<Book> {
    private static final String PRICE_KEY_FIELD = "price";

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) ->
                root.get(PRICE_KEY_FIELD).in(Arrays.stream(params).toArray());
    }

    @Override
    public String getKey() {
        return PRICE_KEY_FIELD;
    }
}
