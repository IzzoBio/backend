package bio.izzo.app.backend.configuration;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import bio.izzo.app.backend.repository.UserRepository;
import bio.izzo.app.backend.service.CustomOAuth2UserService;
import bio.izzo.app.backend.service.CustomUserDetailsService;
import bio.izzo.app.backend.service.JwtService;
import bio.izzo.app.backend.utils.handler.OAuth2LoginSuccessHandler;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
  @Lazy
  @Autowired
  private JwtService jwtService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CustomOAuth2UserService customOAuth2UserService;

  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailsService);
  }

  public SecurityConfiguration(CustomUserDetailsService customUserDetailsService, UserRepository userRepository) {
    this.customUserDetailsService = customUserDetailsService;
    this.userRepository = userRepository;
  }

  @Bean
  @SuppressWarnings("removal")
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(customizer -> {
      customizer
              .requestMatchers(HttpMethod.GET, "/health/**").permitAll()

              .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
              .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
        
              .requestMatchers(HttpMethod.GET, "/api/users/verify-email").permitAll()
              .requestMatchers(HttpMethod.POST, "/api/users/forgot-password").permitAll()
              .requestMatchers(HttpMethod.PATCH, "/api/users/reset-password").permitAll()

              .requestMatchers(HttpMethod.GET, "/api/actualities").permitAll()
              .requestMatchers(HttpMethod.POST, "/api/actualities").hasRole("ADMIN")
              .requestMatchers(HttpMethod.PUT, "/api/actualities").hasRole("ADMIN")
              .requestMatchers(HttpMethod.DELETE, "/api/actualities").hasRole("ADMIN")

              .requestMatchers(HttpMethod.GET, "/api/activities").permitAll()
              .requestMatchers(HttpMethod.POST, "/api/activities").hasRole("ADMIN")
              .requestMatchers(HttpMethod.PUT, "/api/activities").hasRole("ADMIN")
              .requestMatchers(HttpMethod.DELETE, "/api/activities").hasRole("ADMIN")

              .requestMatchers(HttpMethod.GET, "/api/enregistrements").hasRole("USER")
              .requestMatchers(HttpMethod.GET, "/api/enregistrements").hasRole("ADMIN")
              .requestMatchers(HttpMethod.POST, "/api/enregistrements").hasRole("USER")
              .requestMatchers(HttpMethod.PUT, "/api/enregistrements").hasRole("ADMIN")
              .requestMatchers(HttpMethod.DELETE, "/api/enregistrements").hasRole("USER")
              .requestMatchers(HttpMethod.DELETE, "/api/enregistrements").hasRole("ADMIN")

              .requestMatchers(HttpMethod.GET, "/api/cuas").hasRole("ADMIN")
              .requestMatchers(HttpMethod.POST, "/api/cuas").hasRole("ADMIN")
              .requestMatchers(HttpMethod.PUT, "/api/cuas").hasRole("ADMIN")
              .requestMatchers(HttpMethod.DELETE, "/api/cuas").hasRole("ADMIN")

              .requestMatchers(HttpMethod.GET, "/api/requestCUAs").hasRole("ADMIN")
              .requestMatchers(HttpMethod.POST, "/api/requestCUAs").permitAll()
              .requestMatchers(HttpMethod.PUT, "/api/requestCUAs").hasRole("ADMIN")
              .requestMatchers(HttpMethod.DELETE, "/api/requestCUAs").hasRole("ADMIN")

              .requestMatchers(HttpMethod.GET, "/api/trash").permitAll()
              .requestMatchers(HttpMethod.POST, "/api/trash").hasRole("ADMIN")
              .requestMatchers(HttpMethod.PUT, "/api/trash").hasRole("ADMIN")
              .requestMatchers(HttpMethod.DELETE, "/api/trash").hasRole("ADMIN")

              .requestMatchers(HttpMethod.POST, "/api/suggestion").permitAll()
              .requestMatchers(HttpMethod.GET, "/api/suggestion").hasRole("ADMIN")
              .requestMatchers(HttpMethod.PUT, "/api/suggestion").hasRole("ADMIN")
              .requestMatchers(HttpMethod.DELETE, "/api/suggestion").hasRole("ADMIN")

              .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
              .requestMatchers(HttpMethod.GET, "/api/auth/login/google").permitAll()
              .requestMatchers(HttpMethod.GET, "/api/auth/login/github").permitAll()
              
              .anyRequest().authenticated();
    });
    
    http.oauth2Login(oauth2 -> oauth2
          .userInfoEndpoint()
              .userService(customOAuth2UserService)
              .and()
          .successHandler(oauth2LoginSuccessHandler())
          .failureUrl("/login?error=true"));

    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    http.exceptionHandling(customizer -> {
      customizer.authenticationEntryPoint(
          (request, response, authException) -> {
            response.sendError(401, "Unauthorized");
          });
    });

    http.csrf(csrf -> csrf.disable());

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  List<InMemoryUserDetailsManager> userDetailsManager() {
    List<bio.izzo.app.backend.model.entity.User> userList = userRepository.findAll();
    List<InMemoryUserDetailsManager> managers = new ArrayList<>();
    for (bio.izzo.app.backend.model.entity.User user : userList) {
      UserDetails userDetails = User.builder()
          .username(user.getUsername())
          .password(passwordEncoder().encode(user.getPassword()))
          .roles(user.getRole().toString())
          .build();
      managers.add(new InMemoryUserDetailsManager(userDetails));
    }
    return managers;
  }

  @Bean
  JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter(jwtService, customUserDetailsService);
  }

  @Bean
  public OAuth2LoginSuccessHandler oauth2LoginSuccessHandler() {
    return new OAuth2LoginSuccessHandler();
  }
}