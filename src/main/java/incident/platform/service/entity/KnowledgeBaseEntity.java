package incident.platform.service.entity;

import incident.platform.service.constants.PlatformConstants;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "knowledge_base")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exception_name")
    private String exceptionName;

    @Column(name = "root_cause", columnDefinition = "TEXT")
    private String rootCause;

    @Column(name = "resolution", columnDefinition = "TEXT")
    private String resolution;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PlatformConstants.KnowledgeStatus status;

    @Column(name = "source_incident_id")
    private Long sourceIncidentId;

    @CreationTimestamp
    @Column(name = "created__at", updatable = false)
    private LocalDateTime createdDate;
}
