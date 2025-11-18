package com.shoppinglist.controller;

import com.shoppinglist.dto.GastosCategoriaDTO;
import com.shoppinglist.dto.ListaComprasDTO;
import com.shoppinglist.dto.RelatorioGastosPeriodoDTO;
import com.shoppinglist.model.ItemLista;
import com.shoppinglist.model.ListaCompras;
import com.shoppinglist.repository.ListaComprasRepository; // ADICIONE ESTE IMPORT
import com.shoppinglist.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @Autowired // ADICIONE ESTA INJEÇÃO
    private ListaComprasRepository listaComprasRepository;

    // Relatório de gastos por período
    @GetMapping("/gastos-periodo")
    public ResponseEntity<RelatorioGastosPeriodoDTO> getGastosPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        RelatorioGastosPeriodoDTO relatorio = relatorioService.getGastosPorPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(relatorio);
    }

    // Relatório de gastos por categoria
    @GetMapping("/gastos-categoria")
    public ResponseEntity<List<GastosCategoriaDTO>> getGastosPorCategoria(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        List<GastosCategoriaDTO> relatorio = relatorioService.getGastosPorCategoria(dataInicio, dataFim);
        return ResponseEntity.ok(relatorio);
    }

    // Histórico de compras
    @GetMapping("/historico-compras")
    public ResponseEntity<List<ListaComprasDTO>> getHistoricoCompras(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        List<ListaCompras> historico = relatorioService.getHistoricoCompras(dataInicio, dataFim);

        // Converter para DTOs usando o método que já existe
        List<ListaComprasDTO> historicoDTO = historico.stream()
                .map(this::convertToDTO) // Use o método convertToDTO que já existe
                .collect(Collectors.toList());

        return ResponseEntity.ok(historicoDTO);
    }

    // Endpoint de DEBUG - CORRIGIDO
    @GetMapping("/debug")
    public ResponseEntity<String> debugRelatorios(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        try {
            // USE O MÉTODO ORIGINAL POR ENQUANTO
            List<ListaCompras> listas = listaComprasRepository
                    .findByDataCompraRealizadaBetweenAndStatus(dataInicio, dataFim, "CONCLUIDA");

            StringBuilder debugInfo = new StringBuilder();
            debugInfo.append("=== DEBUG RELATÓRIOS ===\n");
            debugInfo.append("Período: ").append(dataInicio).append(" até ").append(dataFim).append("\n");
            debugInfo.append("Listas encontradas: ").append(listas.size()).append("\n\n");

            for (ListaCompras lista : listas) {
                debugInfo.append("Lista: ").append(lista.getNome())
                        .append(", Status: ").append(lista.getStatus())
                        .append(", Data Compra: ").append(lista.getDataCompraRealizada())
                        .append(", Valor Total: ").append(lista.getValorTotalGasto())
                        .append(", Itens: ").append(lista.getItens() != null ? lista.getItens().size() : 0)
                        .append("\n");

                // FORÇAR O CARREGAMENTO DOS ITENS
                if (lista.getItens() != null && !lista.getItens().isEmpty()) {
                    for (ItemLista item : lista.getItens()) {
                        debugInfo.append("  - ").append(item.getNomeProduto())
                                .append(", Comprado: ").append(item.getComprado())
                                .append(", Preço Real: ").append(item.getPrecoReal())
                                .append("\n");
                    }
                } else {
                    debugInfo.append("  - Nenhum item carregado (relacionamento LAZY)\n");
                }
                debugInfo.append("\n");
            }

            return ResponseEntity.ok(debugInfo.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    // Método para converter Entity para DTO
    private ListaComprasDTO convertToDTO(ListaCompras lista) {
        ListaComprasDTO dto = new ListaComprasDTO();
        dto.setListaId(lista.getListaId());
        dto.setNome(lista.getNome());
        dto.setDescricao(lista.getDescricao());
        dto.setDataCompraPrevista(lista.getDataCompraPrevista());
        dto.setDataCompraRealizada(lista.getDataCompraRealizada());
        dto.setOrcamentoTotal(lista.getOrcamentoTotal());
        dto.setValorTotalGasto(lista.getValorTotalGasto());
        dto.setStatus(lista.getStatus());
        dto.setNotificarAntes(lista.getNotificarAntes());
        dto.setUsuarioId(lista.getUsuario().getUsuarioId());
        dto.setDataCriacao(lista.getDataCriacao());
        dto.setDataAtualizacao(lista.getDataAtualizacao());

        return dto;
    }
}