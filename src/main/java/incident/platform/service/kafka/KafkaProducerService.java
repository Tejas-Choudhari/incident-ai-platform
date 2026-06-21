package incident.platform.service.kafka;


import incident.platform.service.constants.PlatformConstants;
import incident.platform.service.entity.IncidentLogEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, IncidentLogEntity>
            kafkaTemplate;

    public void publish(
            IncidentLogEntity entity) {

        kafkaTemplate.send(
                PlatformConstants.INCIDENT_TOPIC,
                entity
        );

        log.info(
                "Incident Published To Kafka : {}",
                entity.getId()
        );
    }
}
