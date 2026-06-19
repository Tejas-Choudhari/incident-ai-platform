package incident.platform.service.repository;

import incident.platform.service.entity.IncidentAnalysisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentAnalysisRepository
        extends JpaRepository<IncidentAnalysisEntity, Long> {
}
