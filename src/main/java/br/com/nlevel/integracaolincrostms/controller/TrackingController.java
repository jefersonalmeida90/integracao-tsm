package br.com.nlevel.integracaolincrostms.controller;

import br.com.nlevel.integracaolincrostms.application.command.ImportarRotaTrackingCommand;
import br.com.nlevel.integracaolincrostms.application.usecase.ImportarRotaTrackingUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/integracao/tracking/rotas")
@Tag(name = "Tracking - Rotas")
@RequiredArgsConstructor
public class TrackingController {

    private final ImportarRotaTrackingUseCase importarRotaTrackingUseCase;

    @PostMapping("/importar")
    @Operation(summary = "Importar rota(s) para o Lincros Tracking")
    public ResponseEntity<Map<String, String>> importar(@Valid @RequestBody ImportarRotaTrackingCommand command) {
        boolean sucesso = importarRotaTrackingUseCase.executar(command);
        return sucesso
                ? ResponseEntity.ok(Map.of("Mensagem", "Rota(s) enviada(s) ao Lincros Tracking."))
                : ResponseEntity.badRequest().build();
    }
}
