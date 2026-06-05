package br.com.nlevel.integracaolincrostms.handler;

import br.com.nlevel.integracaolincrostms.infrastructure.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LincrosApiException.class)
    public ResponseEntity<Map<String, Object>> handleLincrosApiException(LincrosApiException ex, HttpServletRequest req) {
        log.warn("Erro retornado pela API da Lincros com status {}: {}", ex.getStatusCode(), ex.getErroFormatado());

        int httpStatus = mapearStatusCode(ex);
        String titulo = obterTitulo(ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", httpStatus);
        body.put("title", titulo);
        body.put("detail", ex.getErroFormatado());
        body.put("type", "https://httpstatuses.com/" + httpStatus);
        body.put("instance", req.getRequestURI());
        body.put("lincrosStatusCode", ex.getStatusCode());
        if (!ex.getErrors().isEmpty()) {
            body.put("errors", ex.getErrors());
        }

        return ResponseEntity.status(httpStatus)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<Map<String, String>> fieldErrors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    Map<String, String> fieldError = new LinkedHashMap<>();
                    if (error instanceof FieldError fe) {
                        fieldError.put("Campo", fe.getField());
                        fieldError.put("Erro", fe.getDefaultMessage());
                    } else {
                        fieldError.put("Campo", error.getObjectName());
                        fieldError.put("Erro", error.getDefaultMessage());
                    }
                    return fieldError;
                })
                .toList();

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("title", "Erro de validação");
        body.put("instance", req.getRequestURI());
        body.put("errors", fieldErrors);

        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(body);
    }

    private int mapearStatusCode(LincrosApiException ex) {
        if (ex instanceof LincrosValidationException) return HttpStatus.BAD_REQUEST.value();
        if (ex instanceof LincrosUnauthorizedException) return HttpStatus.UNAUTHORIZED.value();
        if (ex instanceof LincrosNotFoundException) return HttpStatus.NOT_FOUND.value();
        if (ex instanceof LincrosUnavailableException) return HttpStatus.BAD_GATEWAY.value();
        if (ex.getStatusCode() >= 500) return HttpStatus.BAD_GATEWAY.value();
        return ex.getStatusCode();
    }

    private String obterTitulo(LincrosApiException ex) {
        if (ex instanceof LincrosValidationException) return "Requisição rejeitada pela Lincros";
        if (ex instanceof LincrosUnauthorizedException) return "Autenticação recusada pela Lincros";
        if (ex instanceof LincrosNotFoundException) return "Recurso não encontrado na Lincros";
        if (ex instanceof LincrosUnavailableException) return "Lincros indisponível";
        return "Erro retornado pela Lincros";
    }
}
