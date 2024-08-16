package bio.izzo.app.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import bio.izzo.app.backend.model.entity.AbstractEntity;
import bio.izzo.app.backend.model.entity.User;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "password_reset_token")
public class PasswordResetToken extends AbstractEntity {

  private String token;
  private boolean emailSent = false;
  private LocalDateTime expiresAt;

  @ManyToOne
  private User user;

  public PasswordResetToken(User user) {
    this.user = user;
    this.token = RandomStringUtils.random(6, false, true);
  }

  public boolean isExpired() {
    return LocalDateTime.now().isAfter(expiresAt);
  }

  public void onEmailSent() {
    this.emailSent = true;
    this.expiresAt = LocalDateTime.now().plusMinutes(10);
  }
}