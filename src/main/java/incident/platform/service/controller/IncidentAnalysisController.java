package incident.platform.service.controller;

import incident.platform.service.dao.IncidentAnalysisResponse;
import incident.platform.service.service.IncidentAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/get")
@RequiredArgsConstructor
public class IncidentAnalysisController {

    private final IncidentAnalysisService service;

    @Operation(operationId = "Get Incident", method = "GET", summary = "Get published log",
            description = "Get published log by using incident_id",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = IncidentAnalysisResponse.class))))
    @GetMapping(value = "/v1/incident/{incidentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public IncidentAnalysisResponse getAnalysis(
            @PathVariable
            Long incidentId) {

        return service.getAnalysis(
                incidentId
        );
    }
}
