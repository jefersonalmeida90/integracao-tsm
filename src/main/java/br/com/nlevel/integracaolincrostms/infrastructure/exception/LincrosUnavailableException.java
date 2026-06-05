package br.com.nlevel.integracaolincrostms.infrastructure.exception;

public class LincrosUnavailableException extends LincrosApiException {

    public LincrosUnavailableException(int statusCode, String message, String responseBody) {
        super(statusCode, message, responseBody);
    }
}
