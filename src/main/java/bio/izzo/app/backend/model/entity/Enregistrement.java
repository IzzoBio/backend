package bio.izzo.app.backend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enregistrement extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "id_actuality")
    private Actuality actuality;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
}
