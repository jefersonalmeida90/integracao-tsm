package br.com.nlevel.integracaolincrostms.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessaoResponse {

    @JsonProperty("id")
    private int id;

    @JsonProperty("nomesessao")
    private String nomeSessao;

    @JsonProperty("inicio")
    private LocalDateTime inicio;

    @JsonProperty("dataEntrega")
    private LocalDateTime dataEntrega;

    @JsonProperty("exportada")
    private boolean exportada;

    @JsonProperty("dataExportacao")
    private LocalDateTime dataExportacao;
}
