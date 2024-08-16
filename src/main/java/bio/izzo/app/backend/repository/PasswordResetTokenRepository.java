package bio.izzo.app.backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import bio.izzo.app.backend.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
  @Query("SELECT prt FROM PasswordResetToken prt WHERE prt.token = ?1")
  Optional<PasswordResetToken> findByToken(String passwordResetToken);
}