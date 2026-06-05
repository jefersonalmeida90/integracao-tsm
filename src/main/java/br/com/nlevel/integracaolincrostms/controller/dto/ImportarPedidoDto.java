package br.com.nlevel.integracaolincrostms.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ImportarPedidoDto {

    @JsonProperty("CodigoPedidoNegocio")
    private String codigoPedidoNegocio;

    @JsonProperty("DataCadastro")
    private LocalDateTime dataCadastro;

    @JsonProperty("Cliente")
    private ClienteDto cliente;

    @JsonProperty("Fornecedor")
    private FornecedorDto fornecedor;

    @JsonProperty("Itens")
    private List<ItemPedidoDto> itens;

    @JsonProperty("PontoInteresse")
    private PontoInteresseDto pontoInteresse;

    @JsonProperty("PontoInteresseRetirada")
    private PontoInteresseDto pontoInteresseRetirada;

    @JsonProperty("Marcadores")
    private List<String> marcadores;

    @JsonProperty("CustoFrete")
    private Double custoFrete;

    @JsonProperty("Unidade")
    private String unidade;

    @JsonProperty("Observacao")
    private String observacao;

    @JsonProperty("CodigoPrioridade")
    private String codigoPrioridade;

    @JsonProperty("IdentificadorPrioridade")
    private String identificadorPrioridade;

    @JsonProperty("SegundosTempoAtendimento")
    private String segundosTempoAtendimento;

    @JsonProperty("Quantidade")
    private Integer quantidade;

    @JsonProperty("QuantidadePeso")
    private Double quantidadePeso;

    @JsonProperty("QuantidadeVolume")
    private Double quantidadeVolume;

    @JsonProperty("Valor")
    private Double valor;

    @JsonProperty("Reentrega")
    private Boolean reentrega;

    @JsonProperty("NaoExportavelGE")
    private Boolean naoExportavelGE;

    @JsonProperty("NaoExportavelERP")
    private Boolean naoExportavelERP;

    @JsonProperty("Tipo")
    private Integer tipo;

    @JsonProperty("Agendamento")
    private LocalDateTime agendamento;

    @JsonProperty("NotaFiscal")
    private String notaFiscal;

    @JsonProperty("ChaveAcesso")
    private String chaveAcesso;

    @JsonProperty("CNPJEmissor")
    private String cnpjEmissor;

    @JsonProperty("NumeroDocumento")
    private String numeroDocumento;

    @JsonProperty("Serie")
    private String serie;

    @JsonProperty("Remetente")
    private String remetente;
}
