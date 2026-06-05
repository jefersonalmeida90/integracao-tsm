package br.com.nlevel.integracaolincrostms.application.usecase;

import br.com.nlevel.integracaolincrostms.domain.model.SessaoResponse;
import br.com.nlevel.integracaolincrostms.domain.port.LincrosApiClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PesquisarSessaoUseCase {

    private final LincrosApiClientPort lincrosClient;

    public List<SessaoResponse> executar(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return lincrosClient.pesquisarSessao(dataInicio, dataFim);
    }
}
