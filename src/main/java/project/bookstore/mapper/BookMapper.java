package project.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.bookstore.config.MapperConfig;
import project.bookstore.dto.BookDto;
import project.bookstore.dto.CreateBookRequestDto;
import project.bookstore.model.Book;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "isbn")
    @Mapping(ignore = true, target = "deleted")
    Book toModel(CreateBookRequestDto dto);
}
