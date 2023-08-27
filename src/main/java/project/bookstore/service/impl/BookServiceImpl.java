package project.bookstore.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import project.bookstore.dto.BookDto;
import project.bookstore.dto.BookDtoWithoutCategoryIds;
import project.bookstore.dto.BookSearchParametersDto;
import project.bookstore.dto.CategoryDto;
import project.bookstore.dto.CreateBookRequestDto;
import project.bookstore.exception.EntityNotFoundException;
import project.bookstore.mapper.BookMapper;
import project.bookstore.mapper.CategoryMapper;
import project.bookstore.model.Book;
import project.bookstore.repository.book.BookRepository;
import project.bookstore.repository.book.BookSpecificationBuilder;
import project.bookstore.service.BookService;
import project.bookstore.service.CategoryService;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    @Override
    public BookDto save(CreateBookRequestDto createBookRequestDto) {
        Book bookForSafe = bookMapper.toModel(createBookRequestDto);
        bookForSafe.setIsbn(UUID.randomUUID().toString());
        return bookMapper.toDto(bookRepository.save(bookForSafe));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAllWithCategories(pageable).stream()
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
    public BookDto updateInfo(Long id, CreateBookRequestDto createBookRequestDto) {
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

    public List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long categoryId, Pageable pageable) {
        return bookRepository.findAllByCategoryId(categoryId, pageable).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }

    @Override
    public void addBookToCategory(Long bookId, Long categoryId) {
        Book bookModelFromDb = getBookModelFromDb(bookId);
        CategoryDto categoryDto = categoryService.findById(categoryId);
        bookModelFromDb.getCategories().add(categoryMapper.toModel(categoryDto));
        bookRepository.save(bookModelFromDb);
    }

    @Override
    public void removeCategoryFromBook(Long bookId, Long categoryId) {
        Book bookModelFromDb = getBookModelFromDb(bookId);
        CategoryDto categoryDto = categoryService.findById(categoryId);
        bookModelFromDb.getCategories().remove(categoryMapper.toModel(categoryDto));
        bookRepository.save(bookModelFromDb);
    }

    private Book getBookModelFromDb(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Can't find Book by id: %s", id)));
    }
}
