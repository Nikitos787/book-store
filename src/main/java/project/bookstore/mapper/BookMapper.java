package project.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.bookstore.config.MapperConfig;
import project.bookstore.dto.BookDto;
import project.bookstore.dto.BookDtoWithoutCategoryIds;
import project.bookstore.dto.CreateBookRequestDto;
import project.bookstore.model.Book;

@Mapper(config = MapperConfig.class, uses = {CategoryMapper.class})
public interface BookMapper {
    @Mapping(source = "categories", target = "categoryDtos")
    BookDto toDto(Book book);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "isbn")
    @Mapping(ignore = true, target = "deleted")
    @Mapping(ignore = true, target = "categories")
    Book toModel(CreateBookRequestDto dto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);
}
