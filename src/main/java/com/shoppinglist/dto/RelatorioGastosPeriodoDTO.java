package com.shoppinglist.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RelatorioGastosPeriodoDTO {
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private BigDecimal totalGasto;
    private Long totalItensComprados;
    private Integer totalComprasRealizadas;

    public RelatorioGastosPeriodoDTO(LocalDate dataInicio, LocalDate dataFim,
                                     BigDecimal totalGasto, Long totalItensComprados,
                                     Integer totalComprasRealizadas) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.totalGasto = totalGasto;
        this.totalItensComprados = totalItensComprados;
        this.totalComprasRealizadas = totalComprasRealizadas;
    }

    // Getters e Setters
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    public BigDecimal getTotalGasto() { return totalGasto; }
    public void setTotalGasto(BigDecimal totalGasto) { this.totalGasto = totalGasto; }

    public Long getTotalItensComprados() { return totalItensComprados; }
    public void setTotalItensComprados(Long totalItensComprados) { this.totalItensComprados = totalItensComprados; }

    public Integer getTotalComprasRealizadas() { return totalComprasRealizadas; }
    public void setTotalComprasRealizadas(Integer totalComprasRealizadas) { this.totalComprasRealizadas = totalComprasRealizadas; }
}