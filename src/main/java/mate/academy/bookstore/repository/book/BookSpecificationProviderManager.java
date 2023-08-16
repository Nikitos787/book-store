package mate.academy.bookstore.repository.book;

import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.repository.SpecificationProvider;
import mate.academy.bookstore.repository.SpecificationProviderManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> specificationProviderList;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return specificationProviderList.stream()
                .filter(sp -> sp.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Specification provider does not exist for key: %s", key)));
    }
}
