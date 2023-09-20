package project.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.bookstore.config.MapperConfig;
import project.bookstore.dto.UserRegistrationRequestDto;
import project.bookstore.dto.UserResponseDto;
import project.bookstore.model.User;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "roles")
    @Mapping(ignore = true, target = "authorities")
    User toModel(UserRegistrationRequestDto userRegistrationRequestDto);
}
