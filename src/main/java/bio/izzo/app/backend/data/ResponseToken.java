package bio.izzo.app.backend.data;

import lombok.Data;

@Data
public class ResponseToken {
  private final int status = 200;
  private String token;

  public ResponseToken(String token) {
    this.token = token;
  }
}
