package project.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import project.bookstore.dto.BookDtoWithoutCategoryIds;
import project.bookstore.dto.CategoryDto;
import project.bookstore.service.BookService;
import project.bookstore.service.CategoryService;

@Tag(name = "Category management", description = "endpoints for managing categories")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private static final String ADMIN = "ROLE_ADMIN";
    private static final String USER = "ROLE_USER";

    private final CategoryService categoryService;
    private final BookService bookService;

    @PostMapping
    @Operation(summary = "Create new category in db")
    @Secured(ADMIN)
    public CategoryDto createCategory(@RequestBody @Valid
                                      @Parameter(schema =
                                      @Schema(implementation = CategoryDto.class))
                                      CategoryDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @GetMapping
    @Operation(summary = "endpoint for get all categories from db",
            description = "You can use pagination and sorting")
    @Secured(USER)
    public List<CategoryDto> getAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by id")
    @Secured(USER)
    public CategoryDto findById(@PathVariable @Parameter(description = "Category Id") Long id) {
        return categoryService.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "endpoint for update category")
    @Secured(ADMIN)
    public CategoryDto updateCategory(@PathVariable
                                      @Parameter(description = "Category Id") Long id,
                                      @RequestBody
                                      @Parameter(schema =
                                      @Schema(implementation = CategoryDto.class))
                                      CategoryDto categoryDto) {
        return categoryService.update(id, categoryDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category from db", description = "Implement safe delete approach")
    @Secured(ADMIN)
    public void delete(@PathVariable
                       @Parameter(description = "Category Id")
                       Long id) {
        categoryService.delete(id);
    }

    @GetMapping("/{id}/books")
    @Operation(summary = "endpoint for getting all books by category id")
    @Secured(USER)
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(@PathVariable
                                                                @Parameter(
                                                                        description = "Category Id")
                                                                Long id, Pageable pageable) {
        return bookService.findAllByCategoryId(id, pageable);
    }
}
