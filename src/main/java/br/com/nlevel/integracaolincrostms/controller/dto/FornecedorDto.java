package br.com.nlevel.integracaolincrostms.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FornecedorDto {
    @JsonProperty("CodigoNegocio")
    private String codigoNegocio;
    @JsonProperty("Nome")
    private String nome;
    @JsonProperty("Documento")
    private String documento;
    @JsonProperty("PontoInteresse")
    private PontoInteresseDto pontoInteresse;
}
