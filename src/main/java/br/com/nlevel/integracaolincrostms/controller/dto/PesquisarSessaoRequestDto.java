package br.com.nlevel.integracaolincrostms.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PesquisarSessaoRequestDto {

    @JsonProperty("DataInicio")
    private LocalDateTime dataInicio;

    @JsonProperty("DataFim")
    private LocalDateTime dataFim;
}
