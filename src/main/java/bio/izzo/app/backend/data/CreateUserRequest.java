package bio.izzo.app.backend.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import bio.izzo.app.backend.utils.validator.PasswordMatch;

@Data
@PasswordMatch(passwordField = "password", passwordConfirmationField = "passwordConfirmation")
@Builder
public class CreateUserRequest {
  
  @Email
  private String email;
  
  @NotNull
  @Length(min = 8)
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "must contain at least one uppercase letter, one lowercase letter, and one digit.")
  private String password;
  
  private String passwordConfirmation;
  
  private String name;
}