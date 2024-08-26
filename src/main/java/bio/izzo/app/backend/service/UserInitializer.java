package bio.izzo.app.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bio.izzo.app.backend.configuration.ApplicationProperties;
import bio.izzo.app.backend.model.entity.Role;
import bio.izzo.app.backend.model.entity.User;
import bio.izzo.app.backend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInitializer {
  private final UserRepository userRepository;
  private final ApplicationProperties applicationProperties;
  private final PasswordEncoder passwordEncoder;

  @PostConstruct
  public void initializeAdminUser() {
    if (userRepository.findByEmail(applicationProperties.getAdminUserEmail()).isEmpty()) {
      User adminUser = new User();
      adminUser.setName(applicationProperties.getAdminUserName());
      adminUser.setEmail(applicationProperties.getAdminUserEmail());
      adminUser.setPassword(passwordEncoder.encode(applicationProperties.getAdminUserPassword()));
      adminUser.setRole(Role.ADMIN);
      adminUser.setVerified(true);
      userRepository.save(adminUser);
    }
  }
}
