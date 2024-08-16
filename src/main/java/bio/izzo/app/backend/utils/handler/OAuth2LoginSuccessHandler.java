package bio.izzo.app.backend.utils.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import bio.izzo.app.backend.configuration.ApplicationProperties;
import bio.izzo.app.backend.model.entity.User;
import bio.izzo.app.backend.repository.UserRepository;
import bio.izzo.app.backend.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
  @Autowired
  private JwtService jwtService;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ApplicationProperties applicationProperties;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
    Map<String, Object> attributes = oAuth2User.getAttributes();

    String email = (String) attributes.get("email");
    Optional<User> userOp = userRepository.findByEmail(email);
    if (!userOp.isPresent())
      throw new RuntimeException("User not exist in database");
    else {
      String token = jwtService.generateToken(userOp.get());
      Cookie jwtCookie = new Cookie("token", token);
      response.addCookie(jwtCookie);
      response.sendRedirect(applicationProperties.getBaseUrl() + "/profile");
    }
  }
}