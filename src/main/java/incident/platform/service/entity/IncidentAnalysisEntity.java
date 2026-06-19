package incident.platform.service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "incident_analysis")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncidentAnalysisEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long incidentId;

    private String severity;

    @Column(columnDefinition = "TEXT")
    private String rootCause;

    @Column(columnDefinition = "TEXT")
    private String recommendation;

    @Column(columnDefinition = "LONGTEXT")
    private String aiSummary;

    private String modelName;

    private String analysisStatus;
}
