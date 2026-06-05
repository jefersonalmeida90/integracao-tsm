package br.com.nlevel.integracaolincrostms.application.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImportarRotaTrackingCommand {

    @NotBlank
    @Size(max = 20)
    @JsonProperty("Empresa")
    private String empresa;

    @NotEmpty
    @Valid
    @JsonProperty("Rotas")
    private List<RotaTrackingCommand> rotas = new ArrayList<>();

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RotaTrackingCommand {
        @NotBlank @Size(max = 20)
        @JsonProperty("Codigo")
        private String codigo;

        @Size(max = 100)
        @JsonProperty("IdRota")
        private String idRota;

        @NotBlank @Size(max = 200)
        @JsonProperty("NomeRota")
        private String nomeRota;

        @NotNull
        @JsonProperty("DataRota")
        private LocalDateTime dataRota;

        @JsonProperty("DataChegadaPrevista")
        private LocalDateTime dataChegadaPrevista;

        @JsonProperty("DataPartidaPrevista")
        private LocalDateTime dataPartidaPrevista;

        @JsonProperty("DistanciaTotalPrevista")
        private Object distanciaTotalPrevista;

        @NotBlank @Size(max = 7)
        @JsonProperty("PlacaVeiculo")
        private String placaVeiculo;

        @JsonProperty("Observacao")
        private String observacao;

        @JsonProperty("IdentificadorExterno")
        private String identificadorExterno;

        @JsonProperty("CodigoDispositivoTemperatura")
        private Integer codigoDispositivoTemperatura;

        @NotNull @Valid
        @JsonProperty("Transportadora")
        private TransportadoraTrackingCommand transportadora;

        @NotNull @Valid
        @JsonProperty("Deposito")
        private DepositoTrackingCommand deposito;

        @NotNull @Valid
        @JsonProperty("Motorista")
        private MotoristaTrackingCommand motorista;

        @Valid
        @JsonProperty("Ajudantes")
        private List<AjudanteTrackingCommand> ajudantes;

        @NotNull
        @JsonProperty("Tipo")
        private Integer tipo;

        @NotEmpty @Valid
        @JsonProperty("Entregas")
        private List<EntregaTrackingCommand> entregas = new ArrayList<>();
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TransportadoraTrackingCommand {
        @NotBlank @Size(max = 100)
        @JsonProperty("Nome")
        private String nome;

        @NotBlank @Size(min = 14, max = 14)
        @JsonProperty("Documento")
        private String documento;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DepositoTrackingCommand {
        @NotNull
        @JsonProperty("Codigo")
        private Object codigo;

        @NotBlank @Size(max = 200)
        @JsonProperty("Nome")
        private String nome;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MotoristaTrackingCommand {
        @NotNull
        @JsonProperty("Codigo")
        private Object codigo;

        @NotBlank @Size(max = 11)
        @JsonProperty("Documento")
        private String documento;

        @NotBlank @Size(max = 200)
        @JsonProperty("Nome")
        private String nome;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AjudanteTrackingCommand {
        @NotNull
        @JsonProperty("Codigo")
        private Object codigo;

        @NotBlank @Size(max = 200)
        @JsonProperty("Nome")
        private String nome;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EntregaTrackingCommand {
        @JsonProperty("Tomador")
        private TomadorTrackingCommand tomador;

        @NotNull @Valid
        @JsonProperty("Cliente")
        private ClienteTrackingCommand cliente;

        @NotNull
        @JsonProperty("Sequencia")
        private Integer sequencia;

        @JsonProperty("Prateleira")
        private String prateleira;

        @JsonProperty("DistanciaPrevista")
        private Long distanciaPrevista;

        @JsonProperty("DataChegadaPrevista")
        private LocalDateTime dataChegadaPrevista;

        @JsonProperty("DataPartidaPrevista")
        private LocalDateTime dataPartidaPrevista;

        @JsonProperty("Coleta")
        private Boolean coleta;

        @JsonProperty("Observacao")
        private String observacao;

        @NotEmpty @Valid
        @JsonProperty("Pedidos")
        private List<PedidoTrackingCommand> pedidos = new ArrayList<>();
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TomadorTrackingCommand {
        @JsonProperty("Documento")
        private String documento;
        @JsonProperty("Nome")
        private String nome;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ClienteTrackingCommand {
        @NotBlank @Size(max = 50)
        @JsonProperty("Codigo")
        private String codigo;

        @NotBlank @Size(max = 120)
        @JsonProperty("Nome")
        private String nome;

        @NotBlank @Size(max = 200)
        @JsonProperty("Endereco")
        private String endereco;

        @Size(max = 50)
        @JsonProperty("Bairro")
        private String bairro;

        @NotBlank @Size(max = 50)
        @JsonProperty("Cidade")
        private String cidade;

        @NotBlank @Size(min = 2, max = 2)
        @JsonProperty("Estado")
        private String estado;

        @JsonProperty("Contato")
        private String contato;

        @JsonProperty("Telefone")
        private String telefone;

        @JsonProperty("TelefoneCelular")
        private String telefoneCelular;

        @NotBlank @Size(max = 20)
        @JsonProperty("Documento")
        private String documento;

        @NotNull
        @JsonProperty("Tipo")
        private Integer tipo;

        @JsonProperty("Latitude")
        private Double latitude;

        @JsonProperty("Longitude")
        private Double longitude;

        @JsonProperty("CEP")
        private String cep;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PedidoTrackingCommand {
        @NotBlank @Size(max = 50)
        @JsonProperty("Codigo")
        private String codigo;

        @NotEmpty @Valid
        @JsonProperty("NotasFiscais")
        private List<NotaFiscalTrackingCommand> notasFiscais = new ArrayList<>();
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class NotaFiscalTrackingCommand {
        @NotBlank @Size(max = 50)
        @JsonProperty("Numero")
        private String numero;

        @JsonProperty("TipoOperacao")
        private String tipoOperacao;

        @JsonProperty("Observacao")
        private String observacao;

        @JsonProperty("CondicaoPagamento")
        private String condicaoPagamento;

        @JsonProperty("Valor")
        private BigDecimal valor;

        @JsonProperty("Peso")
        private BigDecimal peso;

        @JsonProperty("Volume")
        private BigDecimal volume;

        @NotBlank @Size(min = 14, max = 14)
        @JsonProperty("Documento")
        private String documento;

        @NotBlank @Size(max = 3)
        @JsonProperty("Serie")
        private String serie;

        @JsonProperty("Tomador")
        private TomadorTrackingCommand tomador;

        @JsonProperty("ExigirGrauParentesco")
        private Boolean exigirGrauParentesco;

        @JsonProperty("ExigirTipoDocumento")
        private Boolean exigirTipoDocumento;

        @JsonProperty("ExigirNomeDocumentoResponsavelColeta")
        private Boolean exigirNomeDocumentoResponsavelColeta;

        @JsonProperty("ExigirFotoCanhoto")
        private Boolean exigirFotoCanhoto;

        @JsonProperty("ExigirAssinaturaDigitalizada")
        private Boolean exigirAssinaturaDigitalizada;

        @JsonProperty("ExigirNomeDocumentoRecebedor")
        private Boolean exigirNomeDocumentoRecebedor;

        @Valid
        @JsonProperty("NotaFiscalItens")
        private List<NotaFiscalItemTrackingCommand> notaFiscalItens;

        @JsonProperty("NotaFiscalVolumes")
        private List<NotaFiscalVolumeTrackingCommand> notaFiscalVolumes;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class NotaFiscalItemTrackingCommand {
        @Valid
        @JsonProperty("Produto")
        private ProdutoTrackingCommand produto;

        @JsonProperty("Quantidade")
        private Double quantidade;

        @JsonProperty("Valor")
        private BigDecimal valor;

        @JsonProperty("Peso")
        private BigDecimal peso;

        @JsonProperty("Unidade")
        private String unidade;

        @JsonProperty("TipoProduto")
        private TipoProdutoTrackingCommand tipoProduto;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ProdutoTrackingCommand {
        @NotBlank
        @JsonProperty("Codigo")
        private String codigo;

        @JsonProperty("Descricao")
        private String descricao;

        @JsonProperty("Nome")
        private String nome;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TipoProdutoTrackingCommand {
        @JsonProperty("Codigo")
        private String codigo;

        @JsonProperty("Descricao")
        private String descricao;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class NotaFiscalVolumeTrackingCommand {
        @JsonProperty("CodigoNegocio")
        private String codigoNegocio;

        @JsonProperty("Descricao")
        private String descricao;
    }
}
