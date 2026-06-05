package br.com.nlevel.integracaolincrostms.infrastructure.http.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CancelarPedidoRequest {

    @JsonProperty("IdSessaoRoteirizacao")
    private Integer idSessaoRoteirizacao;

    @JsonProperty("Pedidos")
    private List<String> pedidos;

    @JsonProperty("dataCadastro")
    private String dataCadastro;

    @JsonProperty("dataAgendamento")
    private String dataAgendamento;
}
