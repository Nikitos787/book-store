package project.bookstore.repository.book;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import project.bookstore.dto.BookSearchParametersDto;
import project.bookstore.model.Book;
import project.bookstore.repository.SpecificationBuilder;
import project.bookstore.repository.SpecificationProviderManager;

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
