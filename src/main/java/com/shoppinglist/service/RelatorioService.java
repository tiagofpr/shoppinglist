package com.shoppinglist.service;

import com.shoppinglist.dto.GastosCategoriaDTO;
import com.shoppinglist.dto.RelatorioGastosPeriodoDTO;
import com.shoppinglist.model.ItemLista;
import com.shoppinglist.model.ListaCompras;
import com.shoppinglist.repository.ListaComprasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    @Autowired
    private ListaComprasRepository listaComprasRepository;

    public RelatorioGastosPeriodoDTO getGastosPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        // USE O NOVO MÉTODO QUE CARREGA OS ITENS
        List<ListaCompras> listasNoPeriodo = listaComprasRepository
                .findByDataCompraRealizadaBetweenAndStatusComItens(
                        dataInicio,
                        dataFim,
                        "CONCLUIDA"
                );
// ADICIONE DEBUG PARA VERIFICAR
        System.out.println("=== DEBUG RELATÓRIO ===");
        System.out.println("Período: " + dataInicio + " até " + dataFim);
        System.out.println("Listas encontradas: " + listasNoPeriodo.size());

        for (ListaCompras lista : listasNoPeriodo) {
            System.out.println("Lista: " + lista.getNome() +
                    ", Status: " + lista.getStatus() +
                    ", Valor Total: " + lista.getValorTotalGasto() +
                    ", Itens: " + (lista.getItens() != null ? lista.getItens().size() : 0));
        }

        BigDecimal totalGasto = listasNoPeriodo.stream()
                .map(ListaCompras::getValorTotalGasto)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Long totalItensComprados = listasNoPeriodo.stream()
                .mapToLong(lista -> lista.getItens() != null ?
                        lista.getItens().stream()
                                .filter(item -> item.getComprado() == 'S')
                                .count() : 0)
                .sum();

        return new RelatorioGastosPeriodoDTO(
                dataInicio,
                dataFim,
                totalGasto,
                totalItensComprados,
                listasNoPeriodo.size()
        );
    }

    public List<GastosCategoriaDTO> getGastosPorCategoria(LocalDate dataInicio, LocalDate dataFim) {
        List<ListaCompras> listasNoPeriodo = listaComprasRepository
                .findByDataCompraRealizadaBetweenAndStatusComItens(
                        dataInicio,
                        dataFim,
                        "CONCLUIDA"
                );

        // Agrupar gastos por categoria
        Map<String, BigDecimal> gastosPorCategoria = new HashMap<>();

        for (ListaCompras lista : listasNoPeriodo) {
            if (lista.getItens() != null) {
                for (ItemLista item : lista.getItens()) {
                    if (item.getComprado() == 'S' && item.getPrecoReal() != null) {
                        String categoria = obterCategoriaItem(item);
                        BigDecimal precoReal = item.getPrecoReal();

                        gastosPorCategoria.merge(categoria, precoReal, BigDecimal::add);
                    }
                }
            }
        }

        return gastosPorCategoria.entrySet().stream()
                .map(entry -> new GastosCategoriaDTO(entry.getKey(), entry.getValue()))
                .sorted((a, b) -> b.getTotalGasto().compareTo(a.getTotalGasto()))
                .collect(Collectors.toList());
    }

    // Método auxiliar para obter a categoria do item
    private String obterCategoriaItem(ItemLista item) {
        if (item.getCategoria() != null && item.getCategoria().getNome() != null) {
            return item.getCategoria().getNome();
        } else if (item.getProduto() != null && item.getProduto().getCategoria() != null) {
            return item.getProduto().getCategoria().getNome();
        } else {
            return "Sem Categoria";
        }
    }

    public List<ListaCompras> getHistoricoCompras(LocalDate dataInicio, LocalDate dataFim) {
        return listaComprasRepository
                .findByDataCompraRealizadaBetweenAndStatusComItens(
                        dataInicio,
                        dataFim,
                        "CONCLUIDA"
                );
    }

    // Método adicional: Relatório de gastos por mês
    public Map<String, BigDecimal> getGastosPorMes(int ano) {
        List<ListaCompras> listasDoAno = listaComprasRepository
                .findByDataCompraRealizadaBetweenAndStatus(
                        LocalDate.of(ano, 1, 1),
                        LocalDate.of(ano, 12, 31),
                        "CONCLUIDA"
                );

        Map<String, BigDecimal> gastosPorMes = new TreeMap<>();

        for (ListaCompras lista : listasDoAno) {
            if (lista.getDataCompraRealizada() != null && lista.getValorTotalGasto() != null) {
                String mes = lista.getDataCompraRealizada().getMonth().toString();
                BigDecimal valor = lista.getValorTotalGasto();

                gastosPorMes.merge(mes, valor, BigDecimal::add);
            }
        }

        return gastosPorMes;
    }
}