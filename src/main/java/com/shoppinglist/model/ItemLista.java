package com.shoppinglist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ITENS_LISTA")
public class ItemLista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @NotNull(message = "Lista é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lista_id", nullable = false)
    private ListaCompras lista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(max = 100, message = "Nome do produto deve ter no máximo 100 caracteres")
    @Column(name = "nome_produto", nullable = false)
    private String nomeProduto;

    @Column(name = "quantidade", precision = 8, scale = 3)
    private BigDecimal quantidade = BigDecimal.ONE;

    @Column(name = "unidade_medida", length = 20)
    private String unidadeMedida = "UNIDADE";

    @Column(name = "preco_estimado", precision = 8, scale = 2)
    private BigDecimal precoEstimado;

    @Column(name = "preco_real", precision = 8, scale = 2)
    private BigDecimal precoReal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(name = "comprado", length = 1)
    private Character comprado = 'N'; // MUDADO: Character em vez de String

    @Column(name = "prioridade", length = 10)
    private String prioridade = "MEDIA";

    @Size(max = 255, message = "Observações deve ter no máximo 255 caracteres")
    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_compra")
    private LocalDateTime dataCompra;

    // Construtores
    public ItemLista() {
        this.dataCriacao = LocalDateTime.now();
    }

    public ItemLista(ListaCompras lista, String nomeProduto) {
        this();
        this.lista = lista;
        this.nomeProduto = nomeProduto;
    }

    // Getters e Setters
    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public ListaCompras getLista() { return lista; }
    public void setLista(ListaCompras lista) { this.lista = lista; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

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

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public Character getComprado() { return comprado; }
    public void setComprado(Character comprado) { this.comprado = comprado; }

    public String getPrioridade() { return prioridade; }
    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataCompra() { return dataCompra; }
    public void setDataCompra(LocalDateTime dataCompra) { this.dataCompra = dataCompra; }

    // Métodos utilitários - CORRIGIDOS
    public void marcarComoComprado() {
        this.comprado = 'S'; // MUDADO: Character em vez de String
        this.dataCompra = LocalDateTime.now();
    }

    public void marcarComoNaoComprado() {
        this.comprado = 'N'; // MUDADO: Character em vez de String
        this.dataCompra = null;
    }
}