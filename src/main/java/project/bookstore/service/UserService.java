package project.bookstore.service;

import project.bookstore.dto.UserRegistrationRequestDto;
import project.bookstore.dto.UserResponseDto;
import project.bookstore.exception.RegistrationException;
import project.bookstore.model.User;

public interface UserService {
    UserResponseDto save(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException;

    User findByEmail(String email);

    User findById(Long id);
}
