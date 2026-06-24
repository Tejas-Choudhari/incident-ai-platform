package incident.platform.service.constants;

import jakarta.persistence.Enumerated;


public class PlatformConstants {
    public static final String INCIDENT_TOPIC = "incident-logs";


    public enum KnowledgeStatus {

        PENDING,
        APPROVED,
        REJECTED
    }
}
