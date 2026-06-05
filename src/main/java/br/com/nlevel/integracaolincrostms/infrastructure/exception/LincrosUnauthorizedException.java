package br.com.nlevel.integracaolincrostms.infrastructure.exception;

public class LincrosUnauthorizedException extends LincrosApiException {

    public LincrosUnauthorizedException(int statusCode, String message, String responseBody) {
        super(statusCode, message, responseBody);
    }
}
