package incident.platform.service.controller;


import incident.platform.service.dao.ApiResponse;
import incident.platform.service.dao.LogRequestVO;
import incident.platform.service.service.IncidentLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class IncidentLogController {

    private static final Logger log = LogManager.getLogger(IncidentLogController.class);
    private final IncidentLogService service;

    @Operation(operationId ="Publish Log", method = "POST", summary = "Publish the new Log",
            description = "Create new log with requestBody",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LogRequestVO.class))))
    @PostMapping(value = "/v1/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> publishLog(
            @Valid
            @RequestBody
            LogRequestVO request) {
        log.info("Inside publishLog controller method for traceId :", request.getTraceId());
        return ResponseEntity.ok(
                service.publishLog(request)
        );
    }
}
