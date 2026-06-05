package br.com.nlevel.integracaolincrostms.infrastructure.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class LincrosErrorResponse {

    @JsonProperty("type")
    private String type;

    @JsonProperty("title")
    private String title;

    @JsonProperty("status")
    private int status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("traceId")
    private String traceId;

    @JsonProperty("errors")
    private Map<String, List<String>> errors = new HashMap<>();

    public String formatarParaLog() {
        if (errors != null && !errors.isEmpty()) {
            String detalhes = errors.entrySet().stream()
                    .map(e -> e.getKey() + ": " + String.join(", ", e.getValue()))
                    .collect(Collectors.joining(" | "));
            return title + " | Detalhes: " + detalhes;
        }
        if (message != null && !message.isBlank()) {
            return message;
        }
        return title != null ? title : "";
    }
}
