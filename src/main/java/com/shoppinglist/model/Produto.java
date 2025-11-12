package com.shoppinglist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRODUTOS")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "produto_id")
    private Long produtoId;

    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(max = 100, message = "Nome do produto deve ter no maximo 100 caracteres")
    @Column(name = "nome", nullable = false)
    private String nome;

    @Size(max = 255, message = "Descrição do produto dever ter no máximo 255 caracteres")
    @Column(name = "descricao")
    private String descricao;

    @Size(max = 100, message = "Marca do produto deve ter no máximo 100 caracteres")
    @Column(name = "marca")
    private String marca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(name = "preco_medio")
    private BigDecimal precoMedio;

    @Column(name = "unidade_medida", length = 20)
    private String unidadeMedida = "UNIDADE";

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "ativo", length = 1)
    private String ativo = "S";

    // Construtores
    public Produto() {
        this.dataCriacao = LocalDateTime.now();
    }

    public Produto(String nome, String descricao, Categoria categoria) {
        this();
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    // Getters e Setters
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public BigDecimal getPrecoMedio() { return precoMedio; }
    public void setPrecoMedio(BigDecimal precoMedio) { this.precoMedio = precoMedio; }

    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public String getAtivo() { return ativo; }
    public void setAtivo(String ativo) { this.ativo = ativo; }
}
