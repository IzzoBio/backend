package bio.izzo.app.backend.data;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRequest {
  @NotBlank
  private String name;
}