package com.shoppinglist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "DTO para representar uma lista de compras")
public class ListaComprasDTO {

    private Long listaId;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Schema(description = "Nome da lista de compras", example = "Compras do Mês")
    private String nome;

    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    @Schema(description = "Descrição da lista", example = "Compras mensais de supermercado")
    private String descricao;

    @Schema(description = "Data prevista para realizar as compras")
    private LocalDate dataCompraPrevista;

    @Schema(description = "Data em que as compras foram realizadas")
    private LocalDate dataCompraRealizada;

    @Schema(description = "Orçamento total estimado")
    private BigDecimal orcamentoTotal;

    @Schema(description = "Valor total realmente gasto")
    private BigDecimal valorTotalGasto;

    @Schema(description = "Status da lista", example = "PENDENTE")
    private String status;

    @Schema(description = "Dias de antecedência para notificação", example = "1")
    private Integer notificarAntes;

    @NotNull(message = "Usuário é obrigatório")
    @Schema(description = "ID do usuário proprietário da lista")
    private Long usuarioId;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    @Schema(description = "Itens da lista de compras")
    private List<ItemListaDTO> itens = new ArrayList<>();

    // Construtores
    public ListaComprasDTO() {}

    public ListaComprasDTO(String nome, Long usuarioId) {
        this.nome = nome;
        this.usuarioId = usuarioId;
    }

    // Getters e Setters
    public Long getListaId() { return listaId; }
    public void setListaId(Long listaId) { this.listaId = listaId; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDate getDataCompraPrevista() { return dataCompraPrevista; }
    public void setDataCompraPrevista(LocalDate dataCompraPrevista) { this.dataCompraPrevista = dataCompraPrevista; }

    public LocalDate getDataCompraRealizada() { return dataCompraRealizada; }
    public void setDataCompraRealizada(LocalDate dataCompraRealizada) { this.dataCompraRealizada = dataCompraRealizada; }

    public BigDecimal getOrcamentoTotal() { return orcamentoTotal; }
    public void setOrcamentoTotal(BigDecimal orcamentoTotal) { this.orcamentoTotal = orcamentoTotal; }

    public BigDecimal getValorTotalGasto() { return valorTotalGasto; }
    public void setValorTotalGasto(BigDecimal valorTotalGasto) { this.valorTotalGasto = valorTotalGasto; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getNotificarAntes() { return notificarAntes; }
    public void setNotificarAntes(Integer notificarAntes) { this.notificarAntes = notificarAntes; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public List<ItemListaDTO> getItens() { return itens; }
    public void setItens(List<ItemListaDTO> itens) { this.itens = itens; }
}