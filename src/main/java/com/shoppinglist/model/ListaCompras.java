package com.shoppinglist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "LISTAS_COMPRAS")
public class ListaCompras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lista_id")
    private Long listaId;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Column(name = "nome", nullable = false)
    private String nome;

    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_compra_prevista")
    private LocalDate dataCompraPrevista;

    @Column(name = "data_compra_realizada")
    private LocalDate dataCompraRealizada;

    @Column(name = "orcamento_total", precision = 10, scale = 2)
    private BigDecimal orcamentoTotal;

    @Column(name = "valor_total_gasto", precision = 10, scale = 2)
    private BigDecimal valorTotalGasto;

    @Column(name = "status", length = 20)
    private String status = "PENDENTE";

    @Column(name = "notificar_antes")
    private Integer notificarAntes = 1;

    @NotNull(message = "Usuário é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "lista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemLista> itens = new ArrayList<>();

    // Construtores
    public ListaCompras() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    public ListaCompras(String nome, Usuario usuario) {
        this();
        this.nome = nome;
        this.usuario = usuario;
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

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public List<ItemLista> getItens() { return itens; }
    public void setItens(List<ItemLista> itens) { this.itens = itens; }

    // Métodos utilitários
    public void adicionarItem(ItemLista item) {
        itens.add(item);
        item.setLista(this);
    }

    public void removerItem(ItemLista item) {
        itens.remove(item);
        item.setLista(null);
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }



}