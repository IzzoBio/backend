package bio.izzo.app.backend.utils.validator;

import java.lang.reflect.Field;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

  private String passwordFieldName;

  private String passwordMatchFieldName;

  @Override
  public void initialize(PasswordMatch constraintAnnotation) {
    passwordFieldName = constraintAnnotation.passwordField();
    passwordMatchFieldName = constraintAnnotation.passwordConfirmationField();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    try {
      Class<?> clazz = value.getClass();
      Field passwordField = clazz.getDeclaredField(passwordFieldName);
      Field passwordMatchField = clazz.getDeclaredField(passwordMatchFieldName);
      passwordField.setAccessible(true);
      passwordMatchField.setAccessible(true);

      String password = (String) passwordField.get(value);
      String passwordMatch = (String) passwordMatchField.get(value);

      return password != null && password.equals(passwordMatch);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.getMessage();
      return false;
    }
  }
}