package br.com.nlevel.integracaolincrostms.application.usecase;

import br.com.nlevel.integracaolincrostms.domain.model.ConsultarSessaoResponse;
import br.com.nlevel.integracaolincrostms.domain.port.LincrosApiClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarSessaoUseCase {

    private final LincrosApiClientPort lincrosClient;

    public ConsultarSessaoResponse executar(int idSessao) {
        return lincrosClient.consultarSessao(idSessao);
    }
}
