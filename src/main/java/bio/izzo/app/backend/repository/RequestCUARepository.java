package bio.izzo.app.backend.repository;

import bio.izzo.app.backend.model.entity.RequestCUA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestCUARepository extends JpaRepository<RequestCUA,Long> {
}
