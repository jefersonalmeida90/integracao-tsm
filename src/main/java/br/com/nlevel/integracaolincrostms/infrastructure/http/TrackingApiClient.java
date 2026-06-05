package br.com.nlevel.integracaolincrostms.infrastructure.http;

import br.com.nlevel.integracaolincrostms.domain.port.TrackingApiClientPort;
import br.com.nlevel.integracaolincrostms.infrastructure.exception.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Component
public class TrackingApiClient implements TrackingApiClientPort {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public TrackingApiClient(@Qualifier("trackingRestClient") RestClient trackingRestClient, ObjectMapper objectMapper) {
        this.restClient = trackingRestClient;
        this.objectMapper = objectMapper;
    }

    @Override
    @Retryable(retryFor = {LincrosUnavailableException.class, ResourceAccessException.class},
            maxAttempts = 5,
            backoff = @Backoff(delay = 5000, multiplier = 2, maxDelay = 60000))
    public void importarRota(String payloadJson) {
        restClient.post()
                .uri("/api/Rota")
                .contentType(MediaType.APPLICATION_JSON)
                .body(payloadJson)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, resp) -> {
                    String body = new String(resp.getBody().readAllBytes());
                    throw criarExcecao(resp.getStatusCode(), body);
                })
                .toBodilessEntity();
    }

    private LincrosApiException criarExcecao(HttpStatusCode statusCode, String responseBody) {
        LincrosErrorResponse lincrosError = null;
        try {
            lincrosError = objectMapper.readValue(responseBody, LincrosErrorResponse.class);
        } catch (Exception ignored) {}

        String mensagem = obterMensagemErro(statusCode.value(), responseBody, lincrosError);

        int code = statusCode.value();
        if (code == HttpStatus.BAD_REQUEST.value()) {
            return new LincrosValidationException(mensagem, responseBody, Collections.emptyMap());
        } else if (code == HttpStatus.UNAUTHORIZED.value() || code == HttpStatus.FORBIDDEN.value()) {
            return new LincrosUnauthorizedException(code, mensagem, responseBody);
        } else if (code == HttpStatus.NOT_FOUND.value()) {
            return new LincrosNotFoundException(mensagem, responseBody);
        } else if (code == HttpStatus.BAD_GATEWAY.value()
                || code == HttpStatus.SERVICE_UNAVAILABLE.value()
                || code == HttpStatus.GATEWAY_TIMEOUT.value()
                || code >= 500) {
            return new LincrosUnavailableException(code, mensagem, responseBody);
        }
        return new LincrosApiException(code, mensagem, responseBody);
    }

    private String obterMensagemErro(int statusCode, String responseBody, LincrosErrorResponse lincrosError) {
        if (lincrosError != null && notBlank(lincrosError.getTitle())) {
            return lincrosError.formatarParaLog();
        }
        return notBlank(responseBody) ? responseBody : "Erro HTTP " + statusCode;
    }

    private boolean notBlank(String s) {
        return s != null && !s.isBlank();
    }
}
