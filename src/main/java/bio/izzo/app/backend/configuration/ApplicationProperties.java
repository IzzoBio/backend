package bio.izzo.app.backend.configuration;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {
  private List<String> allowedOrigins;
  private String applicationName;
  private String baseUrl;
  private String loginPageUrl;
  private String loginSuccessUrl;
  private String adminUserName;
  private String adminUserEmail;
  private String adminUserPassword;
}