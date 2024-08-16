package bio.izzo.app.backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Suggestion extends AbstractEntity {
  private String content;
  @ManyToOne
  @JoinColumn(name = "id_user")
  private User user;
}
