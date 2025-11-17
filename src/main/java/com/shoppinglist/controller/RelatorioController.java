package com.shoppinglist.controller;

import com.shoppinglist.dto.GastosCategoriaDTO;
import com.shoppinglist.dto.RelatorioGastosPeriodoDTO;
import com.shoppinglist.model.ListaCompras;
import com.shoppinglist.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
@CrossOrigin(origins = "*")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

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
    public ResponseEntity<List<ListaCompras>> getHistoricoCompras( // CORRIGIDO: ListaCompras
                                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
                                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        List<ListaCompras> historico = relatorioService.getHistoricoCompras(dataInicio, dataFim);
        return ResponseEntity.ok(historico);
    }
}
