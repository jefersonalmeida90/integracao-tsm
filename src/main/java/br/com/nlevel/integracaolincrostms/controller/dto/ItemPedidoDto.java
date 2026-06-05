package br.com.nlevel.integracaolincrostms.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemPedidoDto {
    @JsonProperty("Quantidade")
    private Integer quantidade;
    @JsonProperty("Tamanho")
    private Double tamanho;
    @JsonProperty("Peso")
    private Double peso;
    @JsonProperty("Identificador")
    private String identificador;
    @JsonProperty("Sequencia")
    private String sequencia;
    @JsonProperty("Valor")
    private Double valor;
    @JsonProperty("CodigoProduto")
    private String codigoProduto;
    @JsonProperty("DescricaoProduto")
    private String descricaoProduto;
}
