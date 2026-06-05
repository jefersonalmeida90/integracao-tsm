package br.com.nlevel.integracaolincrostms.application.usecase;

import br.com.nlevel.integracaolincrostms.domain.port.LincrosApiClientPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CancelarPedidoUseCase {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private final LincrosApiClientPort lincrosClient;

    public boolean executar(Integer idSessao, List<String> pedidos, LocalDateTime dataCadastro, LocalDateTime dataAgendamento) {
        log.info("Iniciando exclusão de {} pedido(s)", pedidos.size());

        String dataCadStr = dataCadastro != null ? dataCadastro.format(FMT) : null;
        String dataAgendStr = dataAgendamento != null ? dataAgendamento.format(FMT) : null;

        lincrosClient.cancelarPedido(idSessao, pedidos, dataCadStr, dataAgendStr);
        return true;
    }
}
