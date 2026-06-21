package incident.platform.service.controller;


import incident.platform.service.dao.ApiResponse;
import incident.platform.service.dao.LogRequestVO;
import incident.platform.service.service.IncidentLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class IncidentLogController {

    private final IncidentLogService service;

    @PostMapping
    public ResponseEntity<ApiResponse> publishLog(
            @Valid
            @RequestBody
            LogRequestVO request) {

        return ResponseEntity.ok(
                service.publishLog(request)
        );
    }
}
