package incident.platform.service.ai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import incident.platform.service.config.OpenRouterProperties;
import incident.platform.service.constants.PlatformConstants;
import incident.platform.service.dao.AiAnalysisResponse;
import incident.platform.service.entity.IncidentAnalysisEntity;
import incident.platform.service.entity.IncidentLogEntity;
import incident.platform.service.entity.KnowledgeBaseEntity;
import incident.platform.service.repository.IncidentAnalysisRepository;
import incident.platform.service.repository.KnowledgeBaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

/**
 * This class is for AIAnalysisService manage AI related configuration
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AIAnalysisService {

    private final OpenRouterProperties properties;
    private final IncidentAnalysisRepository analysisRepository;
    private final ObjectMapper objectMapper;
    private final KnowledgeBaseRepository knowledgeBaseRepository;

    /**
     * This method is for analyze Incident
     *
     * @param incidentLogEntity
     */
    public void analyzeIncident(
            IncidentLogEntity incidentLogEntity) {

        KnowledgeBaseEntity knowledge  =
                knowledgeBaseRepository.findByExceptionName(incidentLogEntity.getExceptionName());

        String prompt = buildPrompt(incidentLogEntity, knowledge);
        log.info("Knowledge Base Match Found : {}",
                knowledge != null ? knowledge.getExceptionName() : "NO_MATCH"
        );

        WebClient webClient =
                WebClient.builder()
                        .baseUrl(properties.getUrl())
                        .defaultHeader(
                                HttpHeaders.AUTHORIZATION,
                                "Bearer " + properties.getKey()
                        )
                        .defaultHeader(
                                HttpHeaders.CONTENT_TYPE,
                                MediaType.APPLICATION_JSON_VALUE
                        )
                        .build();

        String requestBody = """
                {
                  "model":"%s",
                  "messages":[
                    {
                      "role":"user",
                      "content":"%s"
                    }
                  ]
                }
                """.formatted(
                properties.getModel(),
                prompt.replace("\"", "\\\"")
        );

        String response =
                webClient.post()
                        .bodyValue(requestBody)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

        JsonNode rootNode =
                null;
        try {
            rootNode = objectMapper.readTree(response);
        } catch (JsonProcessingException e) {
            log.info("Exception while mapping the JSON node");
            throw new RuntimeException(e);
        }

        String aiContent =
                rootNode
                        .path("choices")
                        .get(0)
                        .path("message")
                        .path("content")
                        .asText();

        log.info("AI Content : {}", aiContent);
        try {
            AiAnalysisResponse aiAnalysisResponse1 =
                    objectMapper.readValue(
                            aiContent,
                            AiAnalysisResponse.class
                    );


            IncidentAnalysisEntity analysis =
                    IncidentAnalysisEntity.builder()
                            .incidentId(
                                    incidentLogEntity.getId()
                            )
                            .severity(
                                    aiAnalysisResponse1.getSeverity()
                            )
                            .rootCause(
                                    aiAnalysisResponse1.getRootCause()
                            )
                            .recommendation(
                                    aiAnalysisResponse1.getRecommendation()
                            )
                            .aiSummary(
                                    aiAnalysisResponse1.getSummary()
                            )
                            .modelName(
                                    properties.getModel()
                            )
                            .analysisStatus(
                                    "SUCCESS"
                            )
                            .build();

            analysisRepository.save(analysis);

        } catch (Exception ex) {

            log.error(
                    "Failed to parse AI response",
                    ex
            );

            IncidentAnalysisEntity analysis =
                    IncidentAnalysisEntity.builder()
                            .incidentId(
                                    incidentLogEntity.getId()
                            )
                            .aiSummary(aiContent)
                            .analysisStatus("PARSE_FAILED")
                            .modelName(
                                    properties.getModel()
                            )
                            .build();

            analysisRepository.save(analysis);
        }
    }

    private String buildPrompt(
            IncidentLogEntity incident,
            KnowledgeBaseEntity knowledge) {

        String historicalKnowledge =
                knowledge == null
                        ? "No historical incident found."
                        : """
                        Historical Incident Knowledge
                        
                        Known Root Cause:
                        %s
                        
                        Known Resolution:
                        %s
                        """
                        .formatted(
                                knowledge.getRootCause(),
                                knowledge.getResolution()
                        );

        return """
                You are a Production Support Engineer.
                
                Analyze the following incident.
                
                Service Name: %s
                Environment: %s
                Log Level: %s
                Exception: %s
                Log Message: %s
                Stack Trace: %s
                
                %s
                
                Return ONLY valid JSON.
                
                {
                  "severity":"",
                  "rootCause":"",
                  "recommendation":"",
                  "summary":""
                }
                
                Do not return markdown.
                Do not return explanation.
                Return JSON only.
                """
                .formatted(
                        incident.getServiceName(),
                        incident.getEnvironment(),
                        incident.getLogLevel(),
                        incident.getExceptionName(),
                        incident.getLogMessage(),
                        incident.getStackTrace(),
                        historicalKnowledge
                );
    }
}
