package com.shoppinglist.dto;

import java.math.BigDecimal;

public class GastosCategoriaDTO {
    private String categoria;
    private BigDecimal totalGasto;

    public GastosCategoriaDTO(String categoria, BigDecimal totalGasto) {
        this.categoria = categoria;
        this.totalGasto = totalGasto;
    }

    // Getters e Setters
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public BigDecimal getTotalGasto() { return totalGasto; }
    public void setTotalGasto(BigDecimal totalGasto) { this.totalGasto = totalGasto; }
}