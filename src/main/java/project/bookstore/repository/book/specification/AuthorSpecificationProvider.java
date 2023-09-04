package project.bookstore.repository.book.specification;

import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import project.bookstore.model.Book;
import project.bookstore.repository.SpecificationProvider;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    private static final String AUTHOR_KEY_FIELD = "author";

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) ->
                root.get(AUTHOR_KEY_FIELD).in(Arrays.stream(params).toArray());
    }

    @Override
    public String getKey() {
        return AUTHOR_KEY_FIELD;
    }
}
