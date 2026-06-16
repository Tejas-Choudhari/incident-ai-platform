package incident.platform.service.dao;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogRequestVO {


    private String traceId;

    private String correlationId;

    @NotBlank(message = "Service Name is mandatory")
    private String serviceName;

    private String serviceVersion;

    private String hostName;
    @NotBlank
    private String environment;
    @NotBlank
    private String logLevel;
    @NotBlank
    private String logMessage;

    private String exceptionName;

    private String stackTrace;
}