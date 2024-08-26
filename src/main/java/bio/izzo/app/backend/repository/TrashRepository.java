package bio.izzo.app.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bio.izzo.app.backend.model.entity.Trash;

@Repository
public interface TrashRepository extends JpaRepository<Trash, Long> {
}
