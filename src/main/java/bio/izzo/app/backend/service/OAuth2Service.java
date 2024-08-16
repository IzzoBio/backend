package bio.izzo.app.backend.service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bio.izzo.app.backend.data.LoginRequest;
import bio.izzo.app.backend.model.entity.User;
import bio.izzo.app.backend.repository.UserRepository;
import bio.izzo.app.backend.utils.provider.ApplicationContextProvider;


@Service
@RequiredArgsConstructor
public class OAuth2Service {
  @Autowired
  private final UserRepository userRepository;
  private final JwtService jwtService;

  public String login(LoginRequest request) {
    Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
    PasswordEncoder passwordEncoder = ApplicationContextProvider.bean(PasswordEncoder.class);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      if (passwordEncoder.matches(request.getPassword(), user.getPassword()))
        return jwtService.generateToken(user);
    }
    return null;
  }
}