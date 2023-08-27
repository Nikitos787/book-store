package project.bookstore.repository.book;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import project.bookstore.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @Query("FROM Book b LEFT JOIN FETCH b.categories c WHERE c.id = :categoryId")
    List<Book> findAllByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    @NonNull
    @Query("FROM Book b LEFT JOIN FETCH b.categories WHERE b.id =:id")
    Optional<Book> findById(@Param("id") @NonNull Long id);

    @NonNull
    @Query("FROM Book b LEFT JOIN FETCH b.categories")
    Page<Book> findAllWithCategories(@NonNull Pageable pageable);
}
