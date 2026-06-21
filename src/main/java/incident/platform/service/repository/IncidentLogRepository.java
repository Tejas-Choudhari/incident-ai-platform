package incident.platform.service.repository;

import incident.platform.service.entity.IncidentLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IncidentLogRepository
        extends JpaRepository<IncidentLogEntity, Long> {

    Optional<IncidentLogEntity>
    findByTraceIdAndCorrelationId(
            String traceId,
            String correlationId
    );

}