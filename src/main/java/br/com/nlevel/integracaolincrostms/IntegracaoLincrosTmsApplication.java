package br.com.nlevel.integracaolincrostms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class IntegracaoLincrosTmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntegracaoLincrosTmsApplication.class, args);
    }

}
