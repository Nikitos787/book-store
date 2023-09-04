package project.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import project.bookstore.lib.FieldsValueMatch;
import project.bookstore.lib.ValidEmail;

@Data
@FieldsValueMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords do not match!"
)
public class UserRegistrationRequestDto {
    @NotBlank(message = "email can't be empty or null")
    @ValidEmail
    private String email;
    @NotBlank
    @Size(min = 8, message = "password can't be less than 8")
    private String password;
    private String repeatPassword;
    @NotBlank(message = "first name can't be empty or null")
    private String firstName;
    @NotBlank(message = "last name can't be empty or null")
    private String lastName;
    @NotBlank(message = "shipping address can't be empty or null")
    private String shippingAddress;
}
