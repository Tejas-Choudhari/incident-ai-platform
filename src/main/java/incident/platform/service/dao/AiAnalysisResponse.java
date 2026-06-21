package incident.platform.service.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AiAnalysisResponse {

    private String severity;

    private String rootCause;

    private String recommendation;

    private String summary;
}
