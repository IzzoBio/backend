package bio.izzo.app.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import bio.izzo.app.backend.configuration.ApplicationProperties;
import bio.izzo.app.backend.data.CreateUserRequest;
import bio.izzo.app.backend.data.ForgotPasswordRequest;
import bio.izzo.app.backend.data.UpdateUserPasswordRequest;
import bio.izzo.app.backend.data.UpdateUserRequest;
import bio.izzo.app.backend.data.UserResponse;
import bio.izzo.app.backend.model.entity.User;
import bio.izzo.app.backend.service.UserService;
import bio.izzo.app.backend.utils.exceptions.EmailAlreadyExistException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private ApplicationProperties applicationProperties;

  @GetMapping("/user")
  public String user(Authentication authentication) {
    return authentication.getPrincipal().toString();
  }

  @GetMapping
  public PagedModel<UserResponse> getUsers(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
    return userService.getUsers(page, size);
  }

  @GetMapping("/filter")
  public ResponseEntity<List<User>> getAllUsers(
          @RequestParam(required = false) String namePrefix){
    List<User> users = userService.getUsersByNamePrefix(namePrefix);
    return new ResponseEntity<>(users, HttpStatus.OK);
  }


  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody CreateUserRequest request) {
    try {
      UserResponse user = userService.create(request);
      return ResponseEntity.ok(user);
    } catch (EmailAlreadyExistException ex) {
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("error", ex.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
  }

  /**
   * Verify the email of the user, redirect to the login page.
   */
  @GetMapping("/verify-email")
  public RedirectView verifyEmail(@RequestParam String token) {
    userService.verifyEmail(token);
    return new RedirectView(applicationProperties.getLoginPageUrl());
  }

  /**
   * Request a password reset email
   */
  @PostMapping("/forgot-password")
  public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequest req) {
    userService.forgotPassword(req.getEmail());
    return ResponseEntity.ok().build();
  }

  /**
   * Reset the password of an existing user, uses the password reset token
   * <p>
   * Only allowed with the password reset token
   */
  @PatchMapping("/reset-password")
  public ResponseEntity<Void> resetPassword(
      @Valid @RequestBody UpdateUserPasswordRequest requestDTO) {
    userService.resetPassword(requestDTO);
    return ResponseEntity.ok().build();
  }

  /**
   * Update an existing user.
   * <p>
   * Only allowed to self.
   */
  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> update(@PathVariable("id") Long id,
      @Valid @RequestBody UpdateUserRequest request) {
    UserResponse user = userService.update(id, request);
    return ResponseEntity.ok(user);
  }

  /**
   * Update the password of an existing user.
   * <p>
   * Only allowed with the correct old password
   */
  @PatchMapping("/password")
  public ResponseEntity<UserResponse> updatePassword(
      @Valid @RequestBody UpdateUserPasswordRequest requestDTO) {
    UserResponse user = userService.updatePassword(requestDTO);
    return ResponseEntity.ok(user);
  }
}