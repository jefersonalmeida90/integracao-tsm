package br.com.nlevel.integracaolincrostms.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PontoInteresseDto {
    @JsonProperty("Endereco")
    private String endereco;
    @JsonProperty("Complemento")
    private String complemento;
    @JsonProperty("Cidade")
    private String cidade;
    @JsonProperty("Bairro")
    private String bairro;
    @JsonProperty("Estado")
    private String estado;
    @JsonProperty("Cep")
    private String cep;
    @JsonProperty("Pais")
    private String pais;
    @JsonProperty("Latitude")
    private Double latitude;
    @JsonProperty("Longitude")
    private Double longitude;
}
