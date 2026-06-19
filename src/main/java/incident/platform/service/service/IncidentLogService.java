package incident.platform.service.service;


import incident.platform.service.dao.LogRequestVO;

public interface IncidentLogService {

    void publishLog(LogRequestVO request);
}