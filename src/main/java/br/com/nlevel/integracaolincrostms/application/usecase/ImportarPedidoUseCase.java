package br.com.nlevel.integracaolincrostms.application.usecase;

import br.com.nlevel.integracaolincrostms.application.command.ImportarPedidoCommand;
import br.com.nlevel.integracaolincrostms.domain.port.LincrosApiClientPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImportarPedidoUseCase {

    private final LincrosApiClientPort lincrosClient;
    private final ObjectMapper objectMapper;

    public boolean executar(ImportarPedidoCommand command) {
        log.info("Enviando pedido {} para a Lincros", command.getCodigoPedidoNegocio());
        try {
            String payloadJson = objectMapper.writeValueAsString(command);
            lincrosClient.enviarPedido(payloadJson);
            return true;
        } catch (Exception e) {
            if (e instanceof RuntimeException re) throw re;
            throw new RuntimeException("Erro ao serializar pedido", e);
        }
    }
}
