package project.bookstore.repository;

import org.springframework.data.jpa.domain.Specification;
import project.bookstore.dto.BookSearchParametersDto;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParametersDto bookSearchParametersDto);
}
