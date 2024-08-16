package bio.izzo.app.backend.utils.jobs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jobrunr.jobs.lambdas.JobRequest;
import org.jobrunr.jobs.lambdas.JobRequestHandler;

import bio.izzo.app.backend.utils.handler.SendResetPasswordEmailJobHandler;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SendResetPasswordEmailJob implements JobRequest {

  private Long tokenId;

  @Override
  public Class<? extends JobRequestHandler> getJobRequestHandler() {
    return SendResetPasswordEmailJobHandler.class;
  }
}