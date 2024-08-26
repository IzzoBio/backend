package bio.izzo.app.backend.model.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trash extends AbstractEntity {
  private double lat;
  private double lon;
  private TypeTrash type;
}
