package br.com.nlevel.integracaolincrostms.infrastructure.exception;

public class LincrosNotFoundException extends LincrosApiException {

    public LincrosNotFoundException(String message, String responseBody) {
        super(404, message, responseBody);
    }
}
