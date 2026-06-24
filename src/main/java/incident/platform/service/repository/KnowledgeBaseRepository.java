package incident.platform.service.repository;


import incident.platform.service.constants.PlatformConstants;
import incident.platform.service.entity.KnowledgeBaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KnowledgeBaseRepository
        extends JpaRepository<KnowledgeBaseEntity, Long> {

    KnowledgeBaseEntity
    findByExceptionName(String exceptionName);

}
