package project.bookstore.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import project.bookstore.dto.BookDto;
import project.bookstore.dto.BookDtoWithoutCategoryIds;
import project.bookstore.dto.BookSearchParametersDto;
import project.bookstore.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto createBookRequestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void delete(Long id);

    List<BookDto> searchBooks(BookSearchParametersDto searchParametersDto);

    void removeCategoryFromBook(Long bookId, Long categoryId);

    BookDto updateInfo(Long id, CreateBookRequestDto createBookRequestDto);

    List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long categoryId, Pageable pageable);

    void addBookToCategory(Long bookId, Long categoryId);
}
