package incident.platform.service.kafka;


import incident.platform.service.constants.PlatformConstants;
import incident.platform.service.dao.LogRequestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, LogRequestVO> kafkaTemplate;

    public void publish(LogRequestVO dto) {

        kafkaTemplate.send(
                PlatformConstants.INCIDENT_TOPIC,
                dto
        );

        log.info(
                "Message published to Kafka Topic : {}",
                PlatformConstants.INCIDENT_TOPIC
        );
    }
}
