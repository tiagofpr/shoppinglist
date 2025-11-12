package com.shoppinglist.controller;

import com.shoppinglist.dto.ProdutoDTO;
import com.shoppinglist.model.Categoria;
import com.shoppinglist.model.Produto;
import com.shoppinglist.repository.CategoriaRepository;
import com.shoppinglist.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "Operações relacionadas a produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    @Operation(summary = "Listar todos os produtos ativos")
    public ResponseEntity<List<ProdutoDTO>> listarProdutos() {
        List<Produto> produtos = produtoRepository.findByAtivo("S");
        List<ProdutoDTO> produtosDTO = produtos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(produtosDTO);
    }

    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Listar produtos por categoria")
    public ResponseEntity<List<ProdutoDTO>> listarProdutosPorCategoria(@PathVariable Long categoriaId) {
        List<Produto> produtos = produtoRepository.findByCategoriaCategoriaId(categoriaId);
        List<ProdutoDTO> produtosDTO = produtos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(produtosDTO);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar produtos por nome")
    public ResponseEntity<List<ProdutoDTO>> buscarProdutosPorNome(@RequestParam String nome) {
        List<Produto> produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
        List<ProdutoDTO> produtosDTO = produtos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(produtosDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID")
    public ResponseEntity<ProdutoDTO> buscarProduto(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar um novo produto")
    public ResponseEntity<ProdutoDTO> criarProduto(@RequestBody ProdutoDTO produtoDTO) {
        Categoria categoria = null;
        if (produtoDTO.getCategoriaId() != null) {
            categoria = categoriaRepository.findById(produtoDTO.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        }

        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setMarca(produtoDTO.getMarca());
        produto.setCategoria(categoria);
        produto.setPrecoMedio(produtoDTO.getPrecoMedio());
        produto.setUnidadeMedida(produtoDTO.getUnidadeMedida());

        Produto produtoSalvo = produtoRepository.save(produto);
        return ResponseEntity.ok(toDTO(produtoSalvo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um produto existente")
    public ResponseEntity<ProdutoDTO> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    Categoria categoria = null;
                    if (produtoDTO.getCategoriaId() != null) {
                        categoria = categoriaRepository.findById(produtoDTO.getCategoriaId())
                                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
                    }

                    produto.setNome(produtoDTO.getNome());
                    produto.setDescricao(produtoDTO.getDescricao());
                    produto.setMarca(produtoDTO.getMarca());
                    produto.setCategoria(categoria);
                    produto.setPrecoMedio(produtoDTO.getPrecoMedio());
                    produto.setUnidadeMedida(produtoDTO.getUnidadeMedida());

                    Produto produtoAtualizado = produtoRepository.save(produto);
                    return ResponseEntity.ok(toDTO(produtoAtualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um produto (desativação lógica)")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setAtivo('N');
                    produtoRepository.save(produto);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Método auxiliar para converter Entity para DTO
    private ProdutoDTO toDTO(Produto produto) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setProdutoId(produto.getProdutoId());
        dto.setNome(produto.getNome());
        dto.setDescricao(produto.getDescricao());
        dto.setMarca(produto.getMarca());
        dto.setPrecoMedio(produto.getPrecoMedio());
        dto.setUnidadeMedida(produto.getUnidadeMedida());
        dto.setDataCriacao(produto.getDataCriacao());
        dto.setAtivo(produto.getAtivo());

        if (produto.getCategoria() != null) {
            dto.setCategoriaId(produto.getCategoria().getCategoriaId());
            dto.setCategoriaNome(produto.getCategoria().getNome());
        }

        return dto;
    }
}