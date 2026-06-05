package br.com.nlevel.integracaolincrostms.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

@Configuration
public class LincrosClientConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Bean
    public RestClient lincrosRestClient(
            RoutingTokenInterceptor tokenInterceptor,
            @Value("${lincros.base-url}") String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestInterceptor(tokenInterceptor)
                .build();
    }

    @Bean
    public RestClient trackingRestClient(
            RoutingTokenInterceptor tokenInterceptor,
            @Value("${lincros-tracking.base-url}") String baseUrl,
            @Value("${lincros-tracking.api-key:}") String apiKey,
            @Value("${lincros-tracking.api-key-header-name:apiKey}") String apiKeyHeaderName) {
        var clientBuilder = RestClient.builder()
                .baseUrl(baseUrl)
                .requestInterceptor(tokenInterceptor);
        if (apiKey != null && !apiKey.isBlank()) {
            clientBuilder = clientBuilder.defaultHeader(apiKeyHeaderName, apiKey);
        }
        return clientBuilder.build();
    }
}
