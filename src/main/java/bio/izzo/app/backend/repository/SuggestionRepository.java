package bio.izzo.app.backend.repository;

import bio.izzo.app.backend.model.entity.Suggestion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
}
