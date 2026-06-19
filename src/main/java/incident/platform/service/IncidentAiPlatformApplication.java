package incident.platform.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@ComponentScan("incident.platform.service")
@EnableKafka
public class IncidentAiPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(IncidentAiPlatformApplication.class, args);
	}

}
