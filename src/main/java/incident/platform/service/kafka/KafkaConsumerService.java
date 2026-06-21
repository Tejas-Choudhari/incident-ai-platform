package incident.platform.service.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import incident.platform.service.ai.AIAnalysisService;
import incident.platform.service.dao.LogRequestVO;
import incident.platform.service.entity.IncidentLogEntity;
import incident.platform.service.repository.IncidentLogRepository;
import incident.platform.service.constants.PlatformConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final IncidentLogRepository repository;
    private final ObjectMapper objectMapper;
    private final AIAnalysisService aiAnalysisService;

    @KafkaListener(
            topics = PlatformConstants.INCIDENT_TOPIC,
            groupId = "incident-group"
    )
    public void consume(
            IncidentLogEntity incidentLogEntity) {

        log.info(
                "Received Incident Id : {}",
                incidentLogEntity.getId()
        );

        aiAnalysisService.analyzeIncident(
                incidentLogEntity
        );
    }
}
