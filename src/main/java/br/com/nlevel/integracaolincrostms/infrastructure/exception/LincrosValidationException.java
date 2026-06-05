package br.com.nlevel.integracaolincrostms.infrastructure.exception;

import java.util.Map;

public class LincrosValidationException extends LincrosApiException {

    public LincrosValidationException(String message, String responseBody, Map<String, String[]> errors) {
        super(400, message, responseBody, errors);
    }
}
