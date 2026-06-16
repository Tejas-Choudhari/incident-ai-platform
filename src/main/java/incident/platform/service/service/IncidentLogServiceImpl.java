package incident.platform.service.service;


import incident.platform.service.dao.LogRequestVO;
import incident.platform.service.kafka.KafkaProducerService;
import incident.platform.service.service.IncidentLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncidentLogServiceImpl
        implements IncidentLogService {

    private final KafkaProducerService producerService;

    @Override
    public void publishLog(LogRequestVO request) {
        producerService.publish(request);
    }
}
