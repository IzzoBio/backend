package bio.izzo.app.backend.repository;

import bio.izzo.app.backend.model.entity.Activitie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivitieRepository extends JpaRepository<Activitie,Long> {
}
