package bio.izzo.app.backend.repository;

import bio.izzo.app.backend.model.entity.Enregistrement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnregistrementRepository extends JpaRepository<Enregistrement,Long> {
}
