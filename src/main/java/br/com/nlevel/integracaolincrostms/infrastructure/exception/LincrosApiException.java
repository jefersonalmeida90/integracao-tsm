package br.com.nlevel.integracaolincrostms.infrastructure.exception;

import java.util.Collections;
import java.util.Map;

public class LincrosApiException extends RuntimeException {

    private final int statusCode;
    private final String erroFormatado;
    private final String responseBody;
    private final Map<String, String[]> errors;

    public LincrosApiException(int statusCode, String message, String responseBody, Map<String, String[]> errors) {
        super("A API da Lincros retornou erro " + statusCode + ": " + message);
        this.statusCode = statusCode;
        this.erroFormatado = message;
        this.responseBody = responseBody;
        this.errors = errors != null ? errors : Collections.emptyMap();
    }

    public LincrosApiException(int statusCode, String message, String responseBody) {
        this(statusCode, message, responseBody, Collections.emptyMap());
    }

    public int getStatusCode() { return statusCode; }
    public String getErroFormatado() { return erroFormatado; }
    public String getResponseBody() { return responseBody; }
    public Map<String, String[]> getErrors() { return errors; }
}
