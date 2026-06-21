package incident.platform.service.controller;

import incident.platform.service.dao.IncidentAnalysisResponse;
import incident.platform.service.service.IncidentAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class IncidentAnalysisController {

    private final IncidentAnalysisService service;

    @GetMapping("/{incidentId}")
    public IncidentAnalysisResponse getAnalysis(
            @PathVariable
            Long incidentId) {

        return service.getAnalysis(
                incidentId
        );
    }
}
