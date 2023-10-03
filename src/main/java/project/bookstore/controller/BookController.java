package project.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.bookstore.dto.BookDto;
import project.bookstore.dto.BookSearchParametersDto;
import project.bookstore.dto.CreateBookRequestDto;
import project.bookstore.service.BookService;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private static final String ADMIN = "ROLE_ADMIN";
    private static final String USER = "ROLE_USER";

    private final BookService bookService;

    @PostMapping
    @Operation(summary = "Create new book in db")
    @Secured(ADMIN)
    public BookDto save(@RequestBody @Valid
                        @Parameter(schema = @Schema(implementation = CreateBookRequestDto.class))
                        CreateBookRequestDto createBookRequestDto) {
        return bookService.save(createBookRequestDto);
    }

    @GetMapping
    @Operation(summary = "Get all books from db.",
            description = "You can use pagination and sorting")
    @Secured(USER)
    public List<BookDto> findAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by id")
    @Secured(USER)
    public BookDto findById(@PathVariable @Parameter(description = "Book id") Long id) {
        return bookService.findById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Get books by parameters")
    @Secured(USER)
    public List<BookDto> searchBooks(@Parameter(schema = @Schema(
            implementation = BookSearchParametersDto.class))
                                     BookSearchParametersDto searchParametersDto) {
        return bookService.searchBooks(searchParametersDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update info about book")
    @Secured(ADMIN)
    public BookDto update(@PathVariable
                          @Parameter(description = "Book id") Long id,
                          @RequestBody @Valid
                          @Parameter(schema = @Schema(implementation = CreateBookRequestDto.class))
                          CreateBookRequestDto createBookRequestDto) {
        return bookService.update(id, createBookRequestDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book from db", description = "Implement safe delete approach")
    @Secured(ADMIN)
    public void delete(@PathVariable
                       @Parameter(description = "Book id") Long id) {
        bookService.delete(id);
    }
}
