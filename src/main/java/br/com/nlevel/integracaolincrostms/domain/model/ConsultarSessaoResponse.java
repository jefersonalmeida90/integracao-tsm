package br.com.nlevel.integracaolincrostms.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ConsultarSessaoResponse {

    @JsonProperty("$id")
    private String idReferencia;

    @JsonProperty("dataExportacao")
    private OffsetDateTime dataExportacao;

    @JsonProperty("rotas")
    private List<RotaResponse> rotas = new ArrayList<>();

    @JsonProperty("pedidosNaoRoteirizados")
    private List<PedidoNaoRoteirizadoResponse> pedidosNaoRoteirizados = new ArrayList<>();

    @Data
    public static class RotaResponse {
        @JsonProperty("$id")
        private String idReferencia;
        @JsonProperty("nomeRota")
        private String nomeRota;
        @JsonProperty("placa")
        private String placa;
        @JsonProperty("nomeMotorista")
        private String nomeMotorista;
        @JsonProperty("codigoMotorista")
        private String codigoMotorista;
        @JsonProperty("dataSaida")
        private OffsetDateTime dataSaida;
        @JsonProperty("dataChegada")
        private OffsetDateTime dataChegada;
        @JsonProperty("tipoVeiculo")
        private String tipoVeiculo;
        @JsonProperty("codigoExternoVeiculo")
        private String codigoExternoVeiculo;
        @JsonProperty("documentoTransportadora")
        private String documentoTransportadora;
        @JsonProperty("codigoTransportadora")
        private String codigoTransportadora;
        @JsonProperty("custoFrete")
        private Double custoFrete;
        @JsonProperty("metrosDistanciaTotal")
        private Double metrosDistanciaTotal;
        @JsonProperty("metrosDistanciaRetorno")
        private Double metrosDistanciaRetorno;
        @JsonProperty("segundosRetorno")
        private Integer segundosRetorno;
        @JsonProperty("codigoRota")
        private Long codigoRota;
        @JsonProperty("numeroRota")
        private Integer numeroRota;
        @JsonProperty("exportado")
        private Boolean exportado;
        @JsonProperty("dataExportacao")
        private OffsetDateTime dataExportacao;
        @JsonProperty("custoTotal")
        private Double custoTotal;
        @JsonProperty("atividades")
        private List<AtividadeResponse> atividades = new ArrayList<>();
    }

    @Data
    public static class AtividadeResponse {
        @JsonProperty("$id")
        private String idReferencia;
        @JsonProperty("codigoCliente")
        private String codigoCliente;
        @JsonProperty("sequencia")
        private Integer sequencia;
        @JsonProperty("metrosDistanciaPercorrida")
        private Double metrosDistanciaPercorrida;
        @JsonProperty("dataChegada")
        private OffsetDateTime dataChegada;
        @JsonProperty("dataSaida")
        private OffsetDateTime dataSaida;
        @JsonProperty("pedidos")
        private List<PedidoSessaoResponse> pedidos = new ArrayList<>();
    }

    @Data
    public static class PedidoSessaoResponse {
        @JsonProperty("$id")
        private String idReferencia;
        @JsonProperty("numeroPedido")
        private String numeroPedido;
        @JsonProperty("unidade")
        private String unidade;
        @JsonProperty("peso")
        private Double peso;
        @JsonProperty("capacidade")
        private Double capacidade;
        @JsonProperty("dataPedido")
        private OffsetDateTime dataPedido;
        @JsonProperty("endereco")
        private String endereco;
        @JsonProperty("bairro")
        private String bairro;
        @JsonProperty("cidade")
        private String cidade;
        @JsonProperty("estado")
        private String estado;
        @JsonProperty("latitude")
        private Double latitude;
        @JsonProperty("longitude")
        private Double longitude;
        @JsonProperty("itens")
        private List<ItemSessaoResponse> itens = new ArrayList<>();
    }

    @Data
    public static class ItemSessaoResponse {
        @JsonProperty("$id")
        private String idReferencia;
        @JsonProperty("nomeProduto")
        private String nomeProduto;
        @JsonProperty("identificadorProduto")
        private String identificadorProduto;
        @JsonProperty("nomeTipoProduto")
        private String nomeTipoProduto;
        @JsonProperty("identificadorTipoProduto")
        private String identificadorTipoProduto;
        @JsonProperty("tamanho")
        private Double tamanho;
        @JsonProperty("peso")
        private Double peso;
        @JsonProperty("sequencia")
        private String sequencia;
        @JsonProperty("valor")
        private Double valor;
        @JsonProperty("quantidade")
        private Double quantidade;
    }

    @Data
    public static class PedidoNaoRoteirizadoResponse {
        @JsonProperty("$id")
        private String idReferencia;
        @JsonProperty("codigoCliente")
        private String codigoCliente;
        @JsonProperty("numeroPedido")
        private String numeroPedido;
    }
}
