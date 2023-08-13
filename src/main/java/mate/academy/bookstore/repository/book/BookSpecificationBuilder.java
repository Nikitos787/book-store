package mate.academy.bookstore.repository.book;

import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.BookSearchParametersDto;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.repository.SpecificationBuilder;
import mate.academy.bookstore.repository.SpecificationProviderManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> specificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto bookParams) {
        Specification<Book> specification = Specification.where(null);
        if (bookParams.getAuthors() != null && bookParams.getAuthors().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("author")
                    .getSpecification(bookParams.getAuthors()));
        }
        if (bookParams.getTitles() != null && bookParams.getTitles().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("title")
                    .getSpecification(bookParams.getTitles()));
        }
        if (bookParams.getPrices() != null && bookParams.getPrices().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("price")
                    .getSpecification(bookParams.getPrices()));
        }
        return specification;
    }
}
