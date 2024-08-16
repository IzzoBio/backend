package bio.izzo.app.backend.repository;

import bio.izzo.app.backend.model.entity.Actuality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActualityRepository extends JpaRepository<Actuality,Long> {
}
