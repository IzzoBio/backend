package bio.izzo.app.backend.utils;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import bio.izzo.app.backend.model.entity.User;
import bio.izzo.app.backend.utils.exceptions.ApiException;

@Slf4j
public class SecurityUtil {

  /**
   * Get the authenticated user from the SecurityContextHolder
   * @throws bio.izzo.app.backend.Utils.Exception.ApiException if the user is not found in the SecurityContextHolder
   */
  public static User getAuthenticatedUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof User user) {
      return user;
    } else {
      log.error("User requested but not found in SecurityContextHolder");
      throw ApiException.builder().status(401).message("Authentication required").build();
    }
  }

  public static Optional<User> getOptionalAuthenticatedUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof User user) {
      return Optional.of(user);
    } else {
      return Optional.empty();
    }
  }
}