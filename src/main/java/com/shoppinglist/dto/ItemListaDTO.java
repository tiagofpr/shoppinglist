package com.shoppinglist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "DTO para representar um item da lista de compras")
public class ItemListaDTO {

    private Long itemId;

    @NotNull(message = "Lista é obrigatória")
    @Schema(description = "ID da lista de compras")
    private Long listaId;

    @Schema(description = "ID do produto")
    private Long produtoId;

    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(max = 100, message = "Nome do produto deve ter no máximo 100 caracteres")
    @Schema(description = "Nome do produto", example = "Arroz")
    private String nomeProduto;

    @Schema(description = "Quantidade do item", example = "2.0")
    private BigDecimal quantidade;

    @Schema(description = "Unidade de medida", example = "KILO")
    private String unidadeMedida;

    @Schema(description = "Preço estimado do item", example = "11.00")
    private BigDecimal precoEstimado;

    @Schema(description = "Preço real pago pelo item", example = "10.50")
    private BigDecimal precoReal;

    @Schema(description = "ID da categoria do item")
    private Long categoriaId;

    @Schema(description = "Nome da categoria do item")
    private String categoriaNome;

    @Schema(description = "Indica se o item foi comprado", example = "N")
    private char comprado;

    @Schema(description = "Prioridade do item", example = "ALTA")
    private String prioridade;

    @Size(max = 255, message = "Observações deve ter no máximo 255 caracteres")
    @Schema(description = "Observações sobre o item")
    private String observacoes;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataCompra;

    private LocalDateTime dataAtualizacao;

    // Construtores
    public ItemListaDTO() {}

    public ItemListaDTO(Long listaId, String nomeProduto) {
        this.listaId = listaId;
        this.nomeProduto = nomeProduto;
    }

    // Getters e Setters
    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public Long getListaId() { return listaId; }
    public void setListaId(Long listaId) { this.listaId = listaId; }

    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }

    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }

    public BigDecimal getQuantidade() { return quantidade; }
    public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }

    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }

    public BigDecimal getPrecoEstimado() { return precoEstimado; }
    public void setPrecoEstimado(BigDecimal precoEstimado) { this.precoEstimado = precoEstimado; }

    public BigDecimal getPrecoReal() { return precoReal; }
    public void setPrecoReal(BigDecimal precoReal) { this.precoReal = precoReal; }

    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }

    public String getCategoriaNome() { return categoriaNome; }
    public void setCategoriaNome(String categoriaNome) { this.categoriaNome = categoriaNome; }

    public char getComprado() { return comprado; }
    public void setComprado(char comprado) { this.comprado = comprado; }

    public String getPrioridade() { return prioridade; }
    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataCompra() { return dataCompra; }
    public void setDataCompra(LocalDateTime dataCompra) { this.dataCompra = dataCompra; }

    // Getter e Setter para dataAtualizacao
    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}