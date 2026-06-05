package br.com.nlevel.integracaolincrostms.application.usecase;

import br.com.nlevel.integracaolincrostms.application.command.ImportarRotaTrackingCommand;
import br.com.nlevel.integracaolincrostms.domain.port.TrackingApiClientPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImportarRotaTrackingUseCase {

    private final TrackingApiClientPort trackingClient;
    private final ObjectMapper objectMapper;

    public boolean executar(ImportarRotaTrackingCommand command) {
        log.info("Enviando {} rota(s) da empresa {} para o Lincros Tracking",
                command.getRotas().size(), command.getEmpresa());
        try {
            String payloadJson = objectMapper.writeValueAsString(command);
            trackingClient.importarRota(payloadJson);
            return true;
        } catch (Exception e) {
            if (e instanceof RuntimeException re) throw re;
            throw new RuntimeException("Erro ao serializar rota de tracking", e);
        }
    }
}
