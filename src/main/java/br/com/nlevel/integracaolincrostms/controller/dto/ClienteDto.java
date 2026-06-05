package br.com.nlevel.integracaolincrostms.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ClienteDto {
    @JsonProperty("CodigoNegocio")
    private String codigoNegocio;
    @JsonProperty("Nome")
    private String nome;
    @JsonProperty("Documento")
    private String documento;
    @JsonProperty("PontoInteresse")
    private PontoInteresseDto pontoInteresse;
    @JsonProperty("Janelas")
    private List<JanelaDto> janelas;
    @JsonProperty("SegundosTempoAtendimento")
    private String segundosTempoAtendimento;
    @JsonProperty("IdentificadorTipoCliente")
    private String identificadorTipoCliente;
    @JsonProperty("NomeTipoCliente")
    private String nomeTipoCliente;
    @JsonProperty("PrioridadeEntrega")
    private Integer prioridadeEntrega;
    @JsonProperty("Observacao")
    private String observacao;
}
