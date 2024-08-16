package bio.izzo.app.backend.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bio.izzo.app.backend.model.entity.User;
import bio.izzo.app.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
  private final UserRepository userRepository;

  @Override
  @Transactional
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
    createOAuth2User(oAuth2User);
    return new DefaultOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority("USER")),
        oAuth2User.getAttributes(),
        "email");
  }

  private User createOAuth2User(OAuth2User oAuth2User) {
    String email = oAuth2User.getAttribute("email");
    Optional<User> existingUser = userRepository.findByEmail(email);
    if (existingUser.isPresent())
      return existingUser.get();
    User user = new User(oAuth2User);
    return userRepository.save(user);
  }
}
