package incident.platform.service.repository;

import incident.platform.service.entity.IncidentAnalysisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IncidentAnalysisRepository
        extends JpaRepository<IncidentAnalysisEntity, Long> {

    Optional<IncidentAnalysisEntity> findByIncidentId(
            Long incidentId
    );
}
