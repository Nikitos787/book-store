package project.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.bookstore.dto.UserLoginRequestDto;
import project.bookstore.dto.UserLoginResponseDto;
import project.bookstore.dto.UserRegistrationRequestDto;
import project.bookstore.dto.UserResponseDto;
import project.bookstore.exception.RegistrationException;
import project.bookstore.security.AuthenticationService;

@Tag(name = "Authentication management", description = "endpoints for managing authentication")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "endpoint for authentication")
    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody
                                      @Valid
                                      @Parameter(schema =
                                      @Schema(implementation = UserLoginRequestDto.class))
                                      UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }

    @Operation(summary = "endpoint for registration new user")
    @PostMapping("/registration")
    public UserResponseDto register(@RequestBody
                                    @Valid
                                    @Parameter(schema =
                                    @Schema(implementation = UserRegistrationRequestDto.class))
                                    UserRegistrationRequestDto request)
            throws RegistrationException {
        return authenticationService.register(request);
    }
}
