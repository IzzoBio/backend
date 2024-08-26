package bio.izzo.app.backend.repository;

import bio.izzo.app.backend.model.entity.Enregistrement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnregistrementRepository extends JpaRepository<Enregistrement,Long> {
}
