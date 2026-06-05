package br.com.nlevel.integracaolincrostms.controller;

import br.com.nlevel.integracaolincrostms.application.command.CancelarPedidoCommand;
import br.com.nlevel.integracaolincrostms.application.command.ImportarPedidoCommand;
import br.com.nlevel.integracaolincrostms.application.usecase.CancelarPedidoUseCase;
import br.com.nlevel.integracaolincrostms.application.usecase.ImportarPedidoUseCase;
import br.com.nlevel.integracaolincrostms.controller.dto.ImportarPedidoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/integracao/pedidos")
@Tag(name = "Routing - Pedidos")
@RequiredArgsConstructor
public class PedidosController {

    private final ImportarPedidoUseCase importarPedidoUseCase;
    private final CancelarPedidoUseCase cancelarPedidoUseCase;

    @PostMapping("/importar")
    @Operation(summary = "Importar pedido para a Lincros Routing")
    public ResponseEntity<Map<String, String>> importar(@RequestBody ImportarPedidoDto dto) {
        var command = mapToCommand(dto);
        boolean sucesso = importarPedidoUseCase.executar(command);
        return sucesso
                ? ResponseEntity.ok(Map.of("Mensagem", "Importação processada."))
                : ResponseEntity.badRequest().build();
    }

    @PostMapping("/cancelar")
    @Operation(summary = "Cancelar pedido(s) na Lincros Routing")
    public ResponseEntity<Map<String, String>> cancelar(@RequestBody CancelarPedidoCommand request) {
        boolean sucesso = cancelarPedidoUseCase.executar(
                request.getIdSessaoRoteirizacao(),
                request.getPedidos(),
                request.getDataCadastro(),
                request.getDataAgendamento()
        );
        if (sucesso) {
            return ResponseEntity.ok(Map.of("Mensagem", "Pedido(s) cancelado(s) com sucesso na Lincros."));
        }
        return ResponseEntity.badRequest()
                .body(Map.of("Erro", "Falha ao cancelar o(s) pedido(s). Verifique os logs para o motivo exato."));
    }

    private ImportarPedidoCommand mapToCommand(ImportarPedidoDto dto) {
        var command = new ImportarPedidoCommand();
        command.setCodigoPedidoNegocio(dto.getCodigoPedidoNegocio());
        command.setDataCadastro(dto.getDataCadastro());
        command.setCustoFrete(dto.getCustoFrete());
        command.setObservacao(dto.getObservacao());
        command.setTipo(dto.getTipo());
        command.setUnidade(dto.getUnidade());
        command.setCodigoPrioridade(dto.getCodigoPrioridade());
        command.setIdentificadorPrioridade(dto.getIdentificadorPrioridade());
        command.setSegundosTempoAtendimento(dto.getSegundosTempoAtendimento());
        command.setQuantidade(dto.getQuantidade());
        command.setQuantidadePeso(dto.getQuantidadePeso());
        command.setQuantidadeVolume(dto.getQuantidadeVolume());
        command.setValor(dto.getValor());
        command.setReentrega(dto.getReentrega());
        command.setNaoExportavelGE(dto.getNaoExportavelGE());
        command.setNaoExportavelERP(dto.getNaoExportavelERP());
        command.setAgendamento(dto.getAgendamento());
        command.setNotaFiscal(dto.getNotaFiscal());
        command.setChaveAcesso(dto.getChaveAcesso());
        command.setCnpjEmissor(dto.getCnpjEmissor());
        command.setNumeroDocumento(dto.getNumeroDocumento());
        command.setSerie(dto.getSerie());
        command.setRemetente(dto.getRemetente());
        command.setMarcadores(dto.getMarcadores());

        if (dto.getCliente() != null) {
            var c = new ImportarPedidoCommand.ClienteCommand();
            c.setCodigoNegocio(dto.getCliente().getCodigoNegocio());
            c.setNome(dto.getCliente().getNome());
            c.setDocumento(dto.getCliente().getDocumento());
            if (dto.getCliente().getPontoInteresse() != null) {
                var pi = new ImportarPedidoCommand.PontoInteresseCommand();
                pi.setEndereco(dto.getCliente().getPontoInteresse().getEndereco());
                pi.setCidade(dto.getCliente().getPontoInteresse().getCidade());
                pi.setEstado(dto.getCliente().getPontoInteresse().getEstado());
                pi.setCep(dto.getCliente().getPontoInteresse().getCep());
                c.setPontoInteresse(pi);
            }
            command.setCliente(c);
        }

        if (dto.getFornecedor() != null) {
            var f = new ImportarPedidoCommand.FornecedorCommand();
            f.setCodigoNegocio(dto.getFornecedor().getCodigoNegocio());
            f.setNome(dto.getFornecedor().getNome());
            f.setDocumento(dto.getFornecedor().getDocumento());
            if (dto.getFornecedor().getPontoInteresse() != null) {
                var pi = new ImportarPedidoCommand.PontoInteresseCommand();
                pi.setEndereco(dto.getFornecedor().getPontoInteresse().getEndereco());
                pi.setCidade(dto.getFornecedor().getPontoInteresse().getCidade());
                pi.setEstado(dto.getFornecedor().getPontoInteresse().getEstado());
                pi.setCep(dto.getFornecedor().getPontoInteresse().getCep());
                f.setPontoInteresse(pi);
            }
            command.setFornecedor(f);
        }

        if (dto.getPontoInteresse() != null) {
            var pi = new ImportarPedidoCommand.PontoInteresseCommand();
            pi.setEndereco(dto.getPontoInteresse().getEndereco());
            pi.setCidade(dto.getPontoInteresse().getCidade());
            pi.setEstado(dto.getPontoInteresse().getEstado());
            pi.setCep(dto.getPontoInteresse().getCep());
            command.setPontoInteresse(pi);
        }

        if (dto.getPontoInteresseRetirada() != null) {
            var pi = new ImportarPedidoCommand.PontoInteresseCommand();
            pi.setEndereco(dto.getPontoInteresseRetirada().getEndereco());
            pi.setCidade(dto.getPontoInteresseRetirada().getCidade());
            pi.setEstado(dto.getPontoInteresseRetirada().getEstado());
            pi.setCep(dto.getPontoInteresseRetirada().getCep());
            command.setPontoInteresseRetirada(pi);
        }

        if (dto.getItens() != null) {
            command.setItens(dto.getItens().stream().map(i -> {
                var item = new ImportarPedidoCommand.ItemPedidoCommand();
                item.setQuantidade(i.getQuantidade());
                item.setPeso(i.getPeso());
                item.setTamanho(i.getTamanho());
                item.setCodigoProduto(i.getCodigoProduto());
                item.setDescricaoProduto(i.getDescricaoProduto());
                return item;
            }).toList());
        }

        return command;
    }
}
