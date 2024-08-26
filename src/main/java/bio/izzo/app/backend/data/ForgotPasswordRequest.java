package bio.izzo.app.backend.data;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ForgotPasswordRequest {
  @Email
  private String email;
}