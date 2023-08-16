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
    private static final String AUTHOR_KEY_FIELD = "author";
    private static final String TITLE_KEY_FIELD = "title";
    private static final String PRICE_KEY_FIELD = "price";

    private final SpecificationProviderManager<Book> specificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParametersDto) {
        Specification<Book> specification = Specification.where(null);
        if (searchParametersDto.getAuthors() != null
                && searchParametersDto.getAuthors().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider(AUTHOR_KEY_FIELD)
                    .getSpecification(searchParametersDto.getAuthors()));
        }
        if (searchParametersDto.getTitles() != null
                && searchParametersDto.getTitles().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider(TITLE_KEY_FIELD)
                    .getSpecification(searchParametersDto.getTitles()));
        }
        if (searchParametersDto.getPrices() != null
                && searchParametersDto.getPrices().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider(PRICE_KEY_FIELD)
                    .getSpecification(searchParametersDto.getPrices()));
        }
        return specification;
    }
}
