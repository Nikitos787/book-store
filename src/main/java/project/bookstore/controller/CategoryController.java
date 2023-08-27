package project.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final CategoryService categoryService;
    private final BookService bookService;

    @Operation(summary = "Create new category in db")
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CategoryDto createCategory(@RequestBody @Valid
                                      @Parameter(schema =
                                      @Schema(implementation = CategoryDto.class))
                                      CategoryDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @Operation(summary = "endpoint for get all categories from db",
            description = "You can use pagination and sorting")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public List<CategoryDto> getAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @Operation(summary = "Get category by id")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public CategoryDto findById(@PathVariable @Parameter(description = "Category Id") Long id) {
        return categoryService.findById(id);
    }

    @Operation(summary = "endpoint for update category")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CategoryDto updateCategory(@PathVariable
                                      @Parameter(description = "Category Id") Long id,
                                      @RequestBody
                                      @Parameter(schema =
                                      @Schema(implementation = CategoryDto.class))
                                      CategoryDto categoryDto) {
        return categoryService.update(id, categoryDto);
    }

    @Operation(summary = "Delete category from db", description = "Implement safe delete approach")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(@PathVariable
                       @Parameter(description = "Category Id")
                       Long id) {
        categoryService.delete(id);
    }

    @Operation(summary = "endpoint for getting all books by category id")
    @GetMapping("/{id}/books")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(@PathVariable
                                                                @Parameter(
                                                                        description = "Category Id")
                                                                Long id, Pageable pageable) {
        return bookService.findAllByCategoryId(id, pageable);
    }
}
