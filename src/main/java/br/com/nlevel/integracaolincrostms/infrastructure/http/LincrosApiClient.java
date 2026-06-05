package br.com.nlevel.integracaolincrostms.infrastructure.http;

import br.com.nlevel.integracaolincrostms.domain.model.ConsultarSessaoResponse;
import br.com.nlevel.integracaolincrostms.domain.model.SessaoResponse;
import br.com.nlevel.integracaolincrostms.domain.port.LincrosApiClientPort;
import br.com.nlevel.integracaolincrostms.infrastructure.exception.*;
import br.com.nlevel.integracaolincrostms.infrastructure.http.request.CancelarPedidoRequest;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Component
public class LincrosApiClient implements LincrosApiClientPort {

    private static final DateTimeFormatter LINCROS_DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public LincrosApiClient(@Qualifier("lincrosRestClient") RestClient lincrosRestClient, ObjectMapper objectMapper) {
        this.restClient = lincrosRestClient;
        this.objectMapper = objectMapper;
    }

    @Override
    @Retryable(retryFor = {LincrosUnavailableException.class, ResourceAccessException.class},
            maxAttempts = 5,
            backoff = @Backoff(delay = 5000, multiplier = 2, maxDelay = 60000))
    public void enviarPedido(String payloadJson) {
        restClient.post()
                .uri("/api/cadastros/pedido/importar")
                .contentType(MediaType.APPLICATION_JSON)
                .body(payloadJson)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, resp) -> {
                    String body = new String(resp.getBody().readAllBytes());
                    throw criarExcecao(resp.getStatusCode(), body);
                })
                .toBodilessEntity();
    }

    @Override
    @Retryable(retryFor = {LincrosUnavailableException.class, ResourceAccessException.class},
            maxAttempts = 5,
            backoff = @Backoff(delay = 5000, multiplier = 2, maxDelay = 60000))
    public void cancelarPedido(Integer idSessao, List<String> pedidos, String dataCadastro, String dataAgendamento) {
        var request = new CancelarPedidoRequest(idSessao, pedidos, dataCadastro, dataAgendamento);

        restClient.post()
                .uri("/api/index/cancelarpedido")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, resp) -> {
                    String body = new String(resp.getBody().readAllBytes());
                    throw criarExcecao(resp.getStatusCode(), body);
                })
                .toBodilessEntity();
    }

    @Override
    @Retryable(retryFor = {LincrosUnavailableException.class, ResourceAccessException.class},
            maxAttempts = 5,
            backoff = @Backoff(delay = 5000, multiplier = 2, maxDelay = 60000))
    public List<SessaoResponse> pesquisarSessao(LocalDateTime dataInicio, LocalDateTime dataFim) {
        var requestBody = Map.of(
                "DataInicio", dataInicio.format(LINCROS_DATE_FMT),
                "DataFim", dataFim.format(LINCROS_DATE_FMT)
        );

        String responseBody = restClient.post()
                .uri("/api/consultaSessao/pesquisar")
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, resp) -> {
                    String body = new String(resp.getBody().readAllBytes());
                    throw criarExcecao(resp.getStatusCode(), body);
                })
                .body(String.class);

        if (responseBody == null || responseBody.isBlank()) return Collections.emptyList();
        try {
            return objectMapper.readValue(responseBody, new TypeReference<>() {});
        } catch (Exception e) {
            log.warn("Erro ao desserializar resposta de pesquisarSessao: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    @Retryable(retryFor = {LincrosUnavailableException.class, ResourceAccessException.class},
            maxAttempts = 5,
            backoff = @Backoff(delay = 5000, multiplier = 2, maxDelay = 60000))
    public ConsultarSessaoResponse consultarSessao(int idSessao) {
        String responseBody = restClient.get()
                .uri("/api/consultaSessao/{id}", idSessao)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, resp) -> {
                    String body = new String(resp.getBody().readAllBytes());
                    throw criarExcecao(resp.getStatusCode(), body);
                })
                .body(String.class);

        if (responseBody == null || responseBody.isBlank()) return null;
        try {
            return objectMapper.readValue(responseBody, ConsultarSessaoResponse.class);
        } catch (Exception e) {
            log.warn("Erro ao desserializar resposta de consultarSessao: {}", e.getMessage());
            return null;
        }
    }

    private LincrosApiException criarExcecao(HttpStatusCode statusCode, String responseBody) {
        LincrosErrorResponse lincrosError = null;
        try {
            lincrosError = objectMapper.readValue(responseBody, LincrosErrorResponse.class);
        } catch (Exception ignored) {}

        String mensagem = obterMensagemErro(statusCode.value(), responseBody, lincrosError);
        Map<String, String[]> errors = converterErros(lincrosError);

        int code = statusCode.value();
        if (code == HttpStatus.BAD_REQUEST.value()) {
            return new LincrosValidationException(mensagem, responseBody, errors);
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
        return new LincrosApiException(code, mensagem, responseBody, errors);
    }

    private String obterMensagemErro(int statusCode, String responseBody, LincrosErrorResponse lincrosError) {
        if (lincrosError != null &&
                (notBlank(lincrosError.getTitle()) || (lincrosError.getErrors() != null && !lincrosError.getErrors().isEmpty()))) {
            return lincrosError.formatarParaLog();
        }
        return notBlank(responseBody) ? responseBody : "Erro HTTP " + statusCode;
    }

    private Map<String, String[]> converterErros(LincrosErrorResponse lincrosError) {
        if (lincrosError == null || lincrosError.getErrors() == null || lincrosError.getErrors().isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, String[]> result = new LinkedHashMap<>();
        lincrosError.getErrors().forEach((k, v) -> result.put(k, v.toArray(new String[0])));
        return result;
    }

    private boolean notBlank(String s) {
        return s != null && !s.isBlank();
    }
}
