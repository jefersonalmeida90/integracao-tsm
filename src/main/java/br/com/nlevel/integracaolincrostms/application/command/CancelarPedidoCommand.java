package br.com.nlevel.integracaolincrostms.application.command;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CancelarPedidoCommand {

    private Integer idSessaoRoteirizacao;
    private List<String> pedidos = new ArrayList<>();
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAgendamento;
}
