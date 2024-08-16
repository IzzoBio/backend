package bio.izzo.app.backend.utils.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender emailSender;

  @Value("${mail.from}")
  private String fromAddress;

  public void sendHtmlMessage(List<String> to, String subject, String htmlBody){
    try {
      MimeMessage message = emailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
      helper.setFrom(fromAddress);
      helper.setTo(to.toArray(new String[0]));
      helper.setSubject(subject);
      helper.setText(htmlBody, true);
      emailSender.send(message);
    } catch (MessagingException e) {
      throw new RuntimeException("Error sending email", e);
    }
  }

  public void sendSimpleEmail(List<String> to, String subject, String content) {
    log.info("Sending email to: {}", to);
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(fromAddress);
    message.setTo(to.toArray(new String[0]));
    message.setSubject(subject);
    message.setText(content);
    emailSender.send(message);
  }

}