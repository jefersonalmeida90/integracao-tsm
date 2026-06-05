package br.com.nlevel.integracaolincrostms.controller;

import br.com.nlevel.integracaolincrostms.application.usecase.ConsultarSessaoUseCase;
import br.com.nlevel.integracaolincrostms.application.usecase.PesquisarSessaoUseCase;
import br.com.nlevel.integracaolincrostms.controller.dto.PesquisarSessaoRequestDto;
import br.com.nlevel.integracaolincrostms.domain.model.ConsultarSessaoResponse;
import br.com.nlevel.integracaolincrostms.domain.model.SessaoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/integracao/sessoes")
@Tag(name = "Routing - Sessoes")
@RequiredArgsConstructor
public class SessoesController {

    private final ConsultarSessaoUseCase consultarSessaoUseCase;
    private final PesquisarSessaoUseCase pesquisarSessaoUseCase;

    @GetMapping("/{idSessao}")
    @Operation(summary = "Consultar sessão de roteirização pelo ID")
    public ResponseEntity<?> consultar(@PathVariable int idSessao) {
        if (idSessao <= 0) {
            return ResponseEntity.badRequest()
                    .body(Map.of("Erro", "O id da sessão deve ser maior que zero."));
        }
        ConsultarSessaoResponse resultado = consultarSessaoUseCase.executar(idSessao);
        return resultado == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(resultado);
    }

    @PostMapping("/pesquisar")
    @Operation(summary = "Pesquisar sessões de roteirização por intervalo de data")
    public ResponseEntity<List<SessaoResponse>> pesquisar(@RequestBody PesquisarSessaoRequestDto request) {
        List<SessaoResponse> resultado = pesquisarSessaoUseCase.executar(request.getDataInicio(), request.getDataFim());
        return ResponseEntity.ok(resultado);
    }
}
