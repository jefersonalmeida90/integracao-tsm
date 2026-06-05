package br.com.nlevel.integracaolincrostms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig {

    @Value("${cors.allow-all-origins:false}")
    private boolean allowAllOrigins;

    @Value("${cors.allowed-origins:}")
    private List<String> allowedOrigins;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                var registration = registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedHeaders("*");

                if (allowAllOrigins) {
                    registration.allowedOriginPatterns("*");
                } else if (!allowedOrigins.isEmpty()) {
                    registration.allowedOrigins(allowedOrigins.toArray(new String[0]));
                }
            }
        };
    }
}
