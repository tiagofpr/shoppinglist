package com.shoppinglist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "DTO para representar um produto")
public class ProdutoDTO {

    private Long produtoId;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Schema(description = "Nome do produto", example = "Arroz")
    private String nome;

    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    @Schema(description = "Descrição do produto", example = "Arroz branco tipo 1")
    private String descricao;

    @Size(max = 100, message = "Marca deve ter no máximo 100 caracteres")
    @Schema(description = "Marca do produto", example = "Tio João")
    private String marca;

    @Schema(description = "ID da categoria do produto")
    private Long categoriaId;

    @Schema(description = "Nome da categoria do produto")
    private String categoriaNome;

    @Schema(description = "Preço médio do produto", example = "5.50")
    private BigDecimal precoMedio;

    @Schema(description = "Unidade de medida", example = "KILO")
    private String unidadeMedida;

    private LocalDateTime dataCriacao;
    private char ativo;

    // Construtores
    public ProdutoDTO() {}

    public ProdutoDTO(String nome, String descricao, Long categoriaId) {
        this.nome = nome;
        this.descricao = descricao;
        this.categoriaId = categoriaId;
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

    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }

    public String getCategoriaNome() { return categoriaNome; }
    public void setCategoriaNome(String categoriaNome) { this.categoriaNome = categoriaNome; }

    public BigDecimal getPrecoMedio() { return precoMedio; }
    public void setPrecoMedio(BigDecimal precoMedio) { this.precoMedio = precoMedio; }

    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public char getAtivo() { return ativo; }
    public void setAtivo(char ativo) { this.ativo = ativo; }
}