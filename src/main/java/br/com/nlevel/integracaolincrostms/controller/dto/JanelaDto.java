package br.com.nlevel.integracaolincrostms.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JanelaDto {
    @JsonProperty("Inicio")
    private LocalDateTime inicio;
    @JsonProperty("Termino")
    private LocalDateTime termino;
    @JsonProperty("Segunda")
    private Boolean segunda;
    @JsonProperty("Terca")
    private Boolean terca;
    @JsonProperty("Quarta")
    private Boolean quarta;
    @JsonProperty("Quinta")
    private Boolean quinta;
    @JsonProperty("Sexta")
    private Boolean sexta;
    @JsonProperty("Sabado")
    private Boolean sabado;
    @JsonProperty("Domingo")
    private Boolean domingo;
}
