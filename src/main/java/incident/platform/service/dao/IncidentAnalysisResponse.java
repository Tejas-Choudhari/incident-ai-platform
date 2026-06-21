package incident.platform.service.dao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class IncidentAnalysisResponse {

    private Long incidentId;

    private String severity;

    private String rootCause;

    private String recommendation;

    private String summary;

    private String modelName;

    private String analysisStatus;
}