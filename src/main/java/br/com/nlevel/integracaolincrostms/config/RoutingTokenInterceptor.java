package br.com.nlevel.integracaolincrostms.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

@Component
public class RoutingTokenInterceptor implements ClientHttpRequestInterceptor {

    private final String configuredApiKey;

    public RoutingTokenInterceptor(@Value("${lincros.api-key:}") String configuredApiKey) {
        this.configuredApiKey = configuredApiKey;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        String token = getTokenFromCurrentRequest();
        if (token == null && configuredApiKey != null && !configuredApiKey.isBlank()) {
            token = configuredApiKey;
        }
        if (token != null && !token.isBlank()) {
            request.getHeaders().setBearerAuth(normalizeToken(token));
        }
        return execution.execute(request, body);
    }

    private String getTokenFromCurrentRequest() {
        try {
            var attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest req = attrs.getRequest();
            String authHeader = req.getHeader("Authorization");
            return (authHeader != null && !authHeader.isBlank()) ? authHeader : null;
        } catch (Exception e) {
            return null;
        }
    }

    private String normalizeToken(String token) {
        String trimmed = token.trim();
        if (trimmed.toLowerCase().startsWith("bearer ")) {
            return trimmed.substring(7).trim();
        }
        return trimmed;
    }
}
