package project.bookstore.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.bookstore.dto.UserLoginRequestDto;
import project.bookstore.dto.UserLoginResponseDto;
import project.bookstore.dto.UserRegistrationRequestDto;
import project.bookstore.dto.UserResponseDto;
import project.bookstore.exception.RegistrationException;
import project.bookstore.model.User;
import project.bookstore.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserResponseDto register(
            UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException {
        userRegistrationRequestDto.setPassword(
                passwordEncoder.encode(userRegistrationRequestDto.getPassword())
        );
        return userService.save(userRegistrationRequestDto);
    }

    public UserLoginResponseDto authenticate(
            UserLoginRequestDto authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getLogin(),
                        authenticationRequest.getPassword())
        );
        User user = userService.findByEmail(authenticationRequest.getLogin());
        String jwtToken = jwtService.generateToken(user);
        return new UserLoginResponseDto(jwtToken);
    }
}
