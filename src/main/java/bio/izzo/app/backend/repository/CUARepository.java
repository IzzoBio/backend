package bio.izzo.app.backend.repository;

import bio.izzo.app.backend.model.entity.CUA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CUARepository extends JpaRepository <CUA,Long>  {
}
