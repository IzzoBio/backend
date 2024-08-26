package bio.izzo.app.backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Activitie extends AbstractEntity{
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    private Integer nombreDePoint;
}

