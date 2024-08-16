package bio.izzo.app.backend.service;

import bio.izzo.app.backend.data.CreateUserRequest;
import bio.izzo.app.backend.data.UpdateUserPasswordRequest;
import bio.izzo.app.backend.data.UpdateUserRequest;
import bio.izzo.app.backend.data.UserResponse;
import bio.izzo.app.backend.model.PasswordResetToken;
import bio.izzo.app.backend.model.VerificationCode;
import bio.izzo.app.backend.model.entity.User;
import bio.izzo.app.backend.repository.PasswordResetTokenRepository;
import bio.izzo.app.backend.repository.UserRepository;
import bio.izzo.app.backend.repository.VerificationCodeRepository;
import bio.izzo.app.backend.utils.exceptions.ApiException;
import bio.izzo.app.backend.utils.exceptions.EmailAlreadyExistException;
import bio.izzo.app.backend.utils.jobs.SendResetPasswordEmailJob;
import bio.izzo.app.backend.utils.jobs.SendWelcomeEmailJob;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import org.jobrunr.scheduling.BackgroundJobRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final JwtService jwtService;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;
  @Autowired
  private PagedResourcesAssembler<User> pagedResourcesAssembler;
  private final VerificationCodeRepository verificationCodeRepository;
  private final PasswordResetTokenRepository passwordResetTokenRepository;

  public PagedModel<UserResponse> getUsers(Integer page, Integer size) {
    if (page == null) page = 0;
    if (size == null) size = 10;
    Pageable pageable = PageRequest.of(page, size);
    Page<User> users = userRepository.findAll(pageable);

    return pagedResourcesAssembler.toModel(users, user -> new UserResponse(user));
  }

  @Transactional
  public UserResponse create(@Valid CreateUserRequest request) {
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      throw new EmailAlreadyExistException("Email already exist");
    }
    User user = new User(request);
    user = userRepository.save(user);
    sendVerificationEmail(user);
    return new UserResponse(user);
  }

  private void sendVerificationEmail(User user) {
    VerificationCode verificationCode = new VerificationCode(user);
    user.setVerificationCode(verificationCode);
    verificationCodeRepository.save(verificationCode);
    SendWelcomeEmailJob sendWelcomEmailJob = new SendWelcomeEmailJob(user.getId());
    BackgroundJobRequest.enqueue(sendWelcomEmailJob);
  }

  @Transactional
  public void verifyEmail(String code) {
    VerificationCode verificationCode = verificationCodeRepository.findByCode(code)
        .orElseThrow(() -> ApiException.builder().status(400).message("Invalid token").build());
    User user = verificationCode.getUser();
    user.setVerified(true);
    userRepository.save(user);
    verificationCodeRepository.delete(verificationCode);
  }

  @Transactional
  public void forgotPassword(String email) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> ApiException.builder().status(404).message("User not found").build());
    PasswordResetToken passwordResetToken = new PasswordResetToken(user);
    passwordResetTokenRepository.save(passwordResetToken);
    SendResetPasswordEmailJob sendResetPasswordEmailJob = new SendResetPasswordEmailJob(passwordResetToken.getId());
    BackgroundJobRequest.enqueue(sendResetPasswordEmailJob);
  }

  @Transactional
  public void resetPassword(UpdateUserPasswordRequest request) {
    PasswordResetToken passwordResetToken = passwordResetTokenRepository
        .findByToken(request.getPasswordResetToken())
        .orElseThrow(
            () -> ApiException.builder().status(404).message("Password reset token not found").build());

    if (passwordResetToken.isExpired()) {
      throw ApiException.builder().status(400).message("Password reset token is expired").build();
    }

    User user = passwordResetToken.getUser();
    user.updatePassword(request.getPassword());
    userRepository.save(user);
  }

  @Transactional
  @SuppressWarnings("unused")
  public UserResponse update(long userId, UpdateUserRequest request) {
    Optional<User> userOp = userRepository.findById(userId);
    if (userOp == null)
      return new UserResponse(null);
    User user = userOp.get();
    user = userRepository.getReferenceById(user.getId());
    user.update(request);
    user = userRepository.save(user);
    return new UserResponse(user);
  }

  @Transactional
  public UserResponse updatePassword(UpdateUserPasswordRequest request) {
    Optional<User> userOp = userRepository.findByEmail(request.getEmail());
    if (userOp == null)
      return new UserResponse(null);
    User user = userOp.get();
    if (user.getPassword() != null && !passwordEncoder.matches(request.getOldPassword(), user.getPassword()))
      throw ApiException.builder().status(400).message("Wrong password").build();
    user.updatePassword(request.getPassword());
    user = userRepository.save(user);
    return new UserResponse(user);
  }

  public Object getOAuth2Token() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(Collections.singletonMap("error", "User not authenticated"));
    }

    // Generate JWT for the authenticated user
    String username = authentication.getName();
    String token = jwtService.generateToken((User)userDetailsService.loadUserByUsername(username));

    Map<String, String> response = new HashMap<>();
    response.put("token", token);
    return response;
  }

  public List<User> getUsersByNamePrefix(String namePrefix) {
    List<User> users = userRepository.findAll();

    if (namePrefix != null && !namePrefix.isEmpty()) {
      return users.stream()
              .filter(user -> user.getName().toLowerCase().startsWith(namePrefix.toLowerCase()))
              .collect(Collectors.toList());
    }

    return users;
  }
}
