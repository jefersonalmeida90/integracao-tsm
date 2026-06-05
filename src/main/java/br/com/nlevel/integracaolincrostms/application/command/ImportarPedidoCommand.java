package br.com.nlevel.integracaolincrostms.application.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImportarPedidoCommand {

    @JsonProperty("CodigoPedidoNegocio")
    private String codigoPedidoNegocio;

    @JsonProperty("DataCadastro")
    private LocalDateTime dataCadastro;

    @JsonProperty("Cliente")
    private ClienteCommand cliente;

    @JsonProperty("Fornecedor")
    private FornecedorCommand fornecedor;

    @JsonProperty("Itens")
    private List<ItemPedidoCommand> itens;

    @JsonProperty("PontoInteresse")
    private PontoInteresseCommand pontoInteresse;

    @JsonProperty("PontoInteresseRetirada")
    private PontoInteresseCommand pontoInteresseRetirada;

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

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ClienteCommand {
        @JsonProperty("CodigoNegocio")
        private String codigoNegocio;
        @JsonProperty("Nome")
        private String nome;
        @JsonProperty("Documento")
        private String documento;
        @JsonProperty("PontoInteresse")
        private PontoInteresseCommand pontoInteresse;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FornecedorCommand {
        @JsonProperty("CodigoNegocio")
        private String codigoNegocio;
        @JsonProperty("Nome")
        private String nome;
        @JsonProperty("Documento")
        private String documento;
        @JsonProperty("PontoInteresse")
        private PontoInteresseCommand pontoInteresse;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ItemPedidoCommand {
        @JsonProperty("Quantidade")
        private Integer quantidade;
        @JsonProperty("Peso")
        private Double peso;
        @JsonProperty("Tamanho")
        private Double tamanho;
        @JsonProperty("CodigoProduto")
        private String codigoProduto;
        @JsonProperty("DescricaoProduto")
        private String descricaoProduto;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PontoInteresseCommand {
        @JsonProperty("Endereco")
        private String endereco;
        @JsonProperty("Cidade")
        private String cidade;
        @JsonProperty("Estado")
        private String estado;
        @JsonProperty("Cep")
        private String cep;
    }
}
