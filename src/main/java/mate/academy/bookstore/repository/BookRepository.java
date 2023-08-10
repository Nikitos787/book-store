package mate.academy.bookstore.repository;

import java.util.List;
import mate.academy.bookstore.model.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository {
    Book save(Book book);

    List findAll();
}
