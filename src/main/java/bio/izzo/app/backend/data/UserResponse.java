package bio.izzo.app.backend.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;

import bio.izzo.app.backend.model.entity.Role;
import bio.izzo.app.backend.model.entity.User;

@Data
public class UserResponse extends RepresentationModel<UserResponse> {
  private Long id;
  private Role role;
  private String name;
  private String email;
  private List<String> authorities = new ArrayList<>();

  public UserResponse(User user) {
    this.id = user.getId();
    this.role = user.getRole();
    this.name = user.getName();
    this.email = user.getEmail();
  }

  public UserResponse(User user, Collection<? extends GrantedAuthority> authorities) {
    this.id = user.getId();
    this.role = user.getRole();
    this.name = user.getName();
    this.email = user.getEmail();
    authorities.forEach(authority -> {
      this.authorities.add(authority.getAuthority());
    });
  }
}