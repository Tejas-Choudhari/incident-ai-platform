package incident.platform.service.service;

import incident.platform.service.dao.IncidentAnalysisResponse;
import incident.platform.service.entity.IncidentAnalysisEntity;
import incident.platform.service.exception.AnalysisNotFoundException;
import incident.platform.service.repository.IncidentAnalysisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncidentAnalysisServiceImpl
        implements IncidentAnalysisService {

    private final IncidentAnalysisRepository repository;

    @Override
    public IncidentAnalysisResponse getAnalysis(
            Long incidentId) {

        IncidentAnalysisEntity analysis =
                repository.findByIncidentId(
                                incidentId
                        )
                        .orElseThrow(
                                () -> new AnalysisNotFoundException(
                                        "Analysis not found for incident id : "
                                                + incidentId
                                )
                        );

        return IncidentAnalysisResponse
                .builder()
                .incidentId(
                        analysis.getIncidentId()
                )
                .severity(
                        analysis.getSeverity()
                )
                .rootCause(
                        analysis.getRootCause()
                )
                .recommendation(
                        analysis.getRecommendation()
                )
                .summary(
                        analysis.getAiSummary()
                )
                .modelName(
                        analysis.getModelName()
                )
                .analysisStatus(
                        analysis.getAnalysisStatus()
                )
                .build();
    }
}
