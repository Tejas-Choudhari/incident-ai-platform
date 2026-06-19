package incident.platform.service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "incident_log")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncidentLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trace_id", length = 100)
    private String traceId;

    @Column(name = "correlation_id", length = 100)
    private String correlationId;

    @Column(name = "service_name", length = 100)
    private String serviceName;

    @Column(name = "service_version", length = 100)
    private String serviceVersion;

    @Column(name = "host_name", length = 100)
    private String hostName;

    @Column(name = "environment", length = 100)
    private String environment;

    @Column(name = "log_level", length = 100)
    private String logLevel;

    @Column(name = "log_message", columnDefinition = "TEXT")
    private String logMessage;

    @Column(name = "exception_name", length = 100)
    private String exceptionName;

    @Column(name = "stack_trace", columnDefinition = "LONGTEXT")
    private String stackTrace;

    @Column(name = "source_topic", length = 100)
    private String sourceTopic;

    @Column(name = "event_timestamp")
    private LocalDateTime eventTimestamp;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    public void prePersist() {

        if (eventTimestamp == null) {
            eventTimestamp = LocalDateTime.now();
        }
    }
}