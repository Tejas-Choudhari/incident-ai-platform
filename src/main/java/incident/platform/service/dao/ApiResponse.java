package incident.platform.service.dao;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private String status;

    private String message;

    private Long incidentId;

}