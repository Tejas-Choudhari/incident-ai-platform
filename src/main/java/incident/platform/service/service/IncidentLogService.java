package incident.platform.service.service;


import incident.platform.service.dao.ApiResponse;
import incident.platform.service.dao.LogRequestVO;

public interface IncidentLogService {

    ApiResponse publishLog(
            LogRequestVO request
    );
}