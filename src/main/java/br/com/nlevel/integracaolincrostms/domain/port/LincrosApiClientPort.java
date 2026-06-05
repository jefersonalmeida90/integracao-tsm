package br.com.nlevel.integracaolincrostms.domain.port;

import br.com.nlevel.integracaolincrostms.domain.model.ConsultarSessaoResponse;
import br.com.nlevel.integracaolincrostms.domain.model.SessaoResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface LincrosApiClientPort {

    List<SessaoResponse> pesquisarSessao(LocalDateTime dataInicio, LocalDateTime dataFim);

    ConsultarSessaoResponse consultarSessao(int idSessao);

    void enviarPedido(String payloadJson);

    void cancelarPedido(Integer idSessao, List<String> pedidos, String dataCadastro, String dataAgendamento);
}
