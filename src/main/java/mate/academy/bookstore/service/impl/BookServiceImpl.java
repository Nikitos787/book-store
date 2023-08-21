package mate.academy.bookstore.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.BookDto;
import mate.academy.bookstore.dto.BookSearchParametersDto;
import mate.academy.bookstore.dto.CreateBookRequestDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.mapper.BookMapper;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.repository.book.BookRepository;
import mate.academy.bookstore.repository.book.BookSpecificationBuilder;
import mate.academy.bookstore.service.BookService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto createBookRequestDto) {
        Book bookForSafe = bookMapper.toModel(createBookRequestDto);
        bookForSafe.setIsbn(UUID.randomUUID().toString());
        return bookMapper.toDto(bookRepository.save(bookForSafe));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toDto(getBookModelFromDb(id));
    }

    @Override
    public List<BookDto> searchBooks(BookSearchParametersDto searchParametersDto) {
        Specification<Book> specification = bookSpecificationBuilder.build(searchParametersDto);
        return bookRepository.findAll(specification).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto createBookRequestDto) {
        Book bookFromDb = getBookModelFromDb(id);
        bookFromDb.setAuthor(createBookRequestDto.getAuthor());
        bookFromDb.setPrice(createBookRequestDto.getPrice());
        bookFromDb.setTitle(createBookRequestDto.getTitle());
        bookFromDb.setCoverImage(createBookRequestDto.getCoverImage());
        bookFromDb.setDescription(createBookRequestDto.getDescription());
        return bookMapper.toDto(bookRepository.save(bookFromDb));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    private Book getBookModelFromDb(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find Book by id: " + id));
    }
}
