package project.bookstore.service.impl;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.bookstore.dto.UserRegistrationRequestDto;
import project.bookstore.dto.UserResponseDto;
import project.bookstore.exception.EntityNotFoundException;
import project.bookstore.exception.RegistrationException;
import project.bookstore.mapper.UserMapper;
import project.bookstore.model.Role;
import project.bookstore.model.User;
import project.bookstore.repository.UserRepository;
import project.bookstore.service.RoleService;
import project.bookstore.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;

    @Override
    public UserResponseDto save(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(userRegistrationRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException(
                    String.format("User with such email: %s already exist in db",
                    userRegistrationRequestDto.getEmail()));
        }
        User user = userMapper.toModel(userRegistrationRequestDto);
        user.setRoles(Set.of(roleService.findByRoleName(Role.RoleName.ROLE_USER)));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException(String
                        .format("Can't find user with email: %s in DB", email)));
    }
}