package incident.platform.service.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
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

    @KafkaListener(
            topics = PlatformConstants.INCIDENT_TOPIC,
            groupId = "incident-group"
    )
    public void consume(LogRequestVO dto) {

        log.info("Received DTO : {}", dto);
        IncidentLogEntity incidentLog =
                IncidentLogEntity.builder()
                        .traceId(dto.getTraceId())
                        .correlationId(dto.getCorrelationId())
                        .serviceName(dto.getServiceName())
                        .serviceVersion(dto.getServiceVersion())
                        .hostName(dto.getHostName())
                        .environment(dto.getEnvironment())
                        .logLevel(dto.getLogLevel())
                        .logMessage(dto.getLogMessage())
                        .exceptionName(dto.getExceptionName())
                        .stackTrace(dto.getStackTrace())
                        .sourceTopic(
                                PlatformConstants.INCIDENT_TOPIC
                        )
                        .build();

        repository.save(incidentLog);
        log.info(
                "Incident Log Saved Successfully"
        );
    }
}
