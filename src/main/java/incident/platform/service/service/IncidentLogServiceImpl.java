package incident.platform.service.service;


import incident.platform.service.constants.PlatformConstants;
import incident.platform.service.dao.ApiResponse;
import incident.platform.service.dao.LogRequestVO;
import incident.platform.service.entity.IncidentLogEntity;
import incident.platform.service.kafka.KafkaProducerService;
import incident.platform.service.repository.IncidentLogRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IncidentLogServiceImpl
        implements IncidentLogService {

    private static final Logger log = LogManager.getLogger(IncidentLogServiceImpl.class);
    private final IncidentLogRepository repository;
    private final KafkaProducerService producerService;

    @Override
    public ApiResponse publishLog(LogRequestVO request) {
        Optional<IncidentLogEntity> existingIncident =
                repository.findByTraceIdAndCorrelationId(
                        request.getTraceId(),
                        request.getCorrelationId()
                );
        if (existingIncident.isPresent()) {
            return ApiResponse.builder()
                    .status("DUPLICATE")
                    .message(
                            "Incident already exists for TraceId : "
                                    + request.getTraceId()
                                    + " and CorrelationId : "
                                    + request.getCorrelationId()
                    )
                    .incidentId(
                            existingIncident.get().getId()
                    )
                    .build();
        }

        IncidentLogEntity entity =
                IncidentLogEntity.builder()
                        .traceId(request.getTraceId())
                        .correlationId(request.getCorrelationId())
                        .serviceName(request.getServiceName())
                        .serviceVersion(request.getServiceVersion())
                        .hostName(request.getHostName())
                        .environment(request.getEnvironment())
                        .logLevel(request.getLogLevel())
                        .logMessage(request.getLogMessage())
                        .exceptionName(request.getExceptionName())
                        .stackTrace(request.getStackTrace())
                        .sourceTopic(
                                PlatformConstants.INCIDENT_TOPIC
                        )
                        .build();
        entity = repository.save(entity);
        producerService.publish(entity);
        return ApiResponse.builder()
                .status("SUCCESS")
                .message(
                        "Log published successfully and completed for AI analysis with Incident ID : "
                                + entity.getId())
                .incidentId(
                        entity.getId())
                .build();
    }
}
