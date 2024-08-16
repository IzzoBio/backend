package bio.izzo.app.backend.utils.handler;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.lambdas.JobRequestHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import bio.izzo.app.backend.configuration.ApplicationProperties;
import bio.izzo.app.backend.model.VerificationCode;
import bio.izzo.app.backend.model.entity.User;
import bio.izzo.app.backend.repository.UserRepository;
import bio.izzo.app.backend.repository.VerificationCodeRepository;
import bio.izzo.app.backend.utils.jobs.SendWelcomeEmailJob;
import bio.izzo.app.backend.utils.mail.EmailService;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendWelcomeEmailJobHandler implements JobRequestHandler<SendWelcomeEmailJob> {

  private final UserRepository userRepository;
  private final VerificationCodeRepository verificationCodeRepository;
  private final SpringTemplateEngine templateEngine;
  private final EmailService emailService;
  private final ApplicationProperties applicationProperties;

  @Override
  @Transactional
  public void run(SendWelcomeEmailJob sendWelcomEmailJob) throws Exception {
    User user = userRepository.findById(sendWelcomEmailJob.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
    log.info("Sending welcome email to user with id: {}", sendWelcomEmailJob.getUserId());
    if (user.getVerificationCode() != null && !user.getVerificationCode().isEmailSent()) {
      sendWelcomeEmail(user, user.getVerificationCode());
    }
  }

  private void sendWelcomeEmail(User user, VerificationCode code) {
    String verificationCode = code.getCode();
    Context thymeleafContext = new Context();
    thymeleafContext.setVariable("user", user);
    thymeleafContext.setVariable("verificationCode", verificationCode);
    thymeleafContext.setVariable("applicationName", applicationProperties.getApplicationName());
    String htmlBody = templateEngine.process("welcome-email", thymeleafContext);
    emailService.sendHtmlMessage(List.of(user.getEmail()), "Welcome to Izzo platform", htmlBody);
    code.setEmailSent(true);
    verificationCodeRepository.save(code);
  }
}