package incident.platform.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "openrouter.api")
public class OpenRouterProperties {

    private String key;
    private String url;
    private String model;
}
