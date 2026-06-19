package incident.platform.service.dao;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OpenRouterRequest {

    private String model;

    private List<Message> messages;
}