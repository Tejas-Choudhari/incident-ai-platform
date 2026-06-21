package incident.platform.service.service;

import incident.platform.service.dao.IncidentAnalysisResponse;

public interface IncidentAnalysisService {

    IncidentAnalysisResponse getAnalysis(
            Long incidentId
    );
}
