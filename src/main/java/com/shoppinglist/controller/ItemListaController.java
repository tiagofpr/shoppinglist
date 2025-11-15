package com.shoppinglist.controller;

import com.shoppinglist.dto.ItemListaDTO;
import com.shoppinglist.model.*;
import com.shoppinglist.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/itens-lista")
@Tag(name = "Itens da Lista", description = "Operações relacionadas aos itens da lista de compras")
public class ItemListaController {

    @Autowired
    private ItemListaRepository itemListaRepository;

    @Autowired
    private ListaComprasRepository listaComprasRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/lista/{listaId}")
    @Operation(summary = "Listar itens de uma lista de compras")
    public ResponseEntity<List<ItemListaDTO>> listarItensPorLista(@PathVariable Long listaId) {
        List<ItemLista> itens = itemListaRepository.findByListaListaId(listaId);
        List<ItemListaDTO> itensDTO = itens.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(itensDTO);
    }

    @GetMapping("/lista/{listaId}/pendentes")
    @Operation(summary = "Listar itens pendentes de uma lista")
    public ResponseEntity<List<ItemListaDTO>> listarItensPendentes(@PathVariable Long listaId) {
        List<ItemLista> itens = itemListaRepository.findByListaListaIdAndComprado(listaId, 'N');
        List<ItemListaDTO> itensDTO = itens.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(itensDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar item por ID")
    public ResponseEntity<ItemListaDTO> buscarItem(@PathVariable Long id) {
        return itemListaRepository.findById(id)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Adicionar item à lista de compras")
    public ResponseEntity<ItemListaDTO> adicionarItem(@RequestBody ItemListaDTO itemDTO) {
        ListaCompras lista = listaComprasRepository.findById(itemDTO.getListaId())
                .orElseThrow(() -> new RuntimeException("Lista de compras não encontrada"));

        Produto produto = null;
        if (itemDTO.getProdutoId() != null) {
            produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElse(null);
        }

        Categoria categoria = null;
        if (itemDTO.getCategoriaId() != null) {
            categoria = categoriaRepository.findById(itemDTO.getCategoriaId())
                    .orElse(null);
        }

        ItemLista item = new ItemLista();
        item.setLista(lista);
        item.setProduto(produto);
        item.setNomeProduto(itemDTO.getNomeProduto());
        item.setQuantidade(itemDTO.getQuantidade());
        item.setUnidadeMedida(itemDTO.getUnidadeMedida());
        item.setPrecoEstimado(itemDTO.getPrecoEstimado());
        item.setPrecoReal(itemDTO.getPrecoReal());
        item.setCategoria(categoria);
        item.setPrioridade(itemDTO.getPrioridade());
        item.setObservacoes(itemDTO.getObservacoes());

        ItemLista itemSalvo = itemListaRepository.save(item);
        return ResponseEntity.ok(toDTO(itemSalvo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um item da lista")
    public ResponseEntity<ItemListaDTO> atualizarItem(@PathVariable Long id, @RequestBody ItemListaDTO itemDTO) {
        return itemListaRepository.findById(id)
                .map(item -> {
                    Produto produto = null;
                    if (itemDTO.getProdutoId() != null) {
                        produto = produtoRepository.findById(itemDTO.getProdutoId())
                                .orElse(null);
                    }

                    Categoria categoria = null;
                    if (itemDTO.getCategoriaId() != null) {
                        categoria = categoriaRepository.findById(itemDTO.getCategoriaId())
                                .orElse(null);
                    }

                    item.setProduto(produto);
                    item.setNomeProduto(itemDTO.getNomeProduto());
                    item.setQuantidade(itemDTO.getQuantidade());
                    item.setUnidadeMedida(itemDTO.getUnidadeMedida());
                    item.setPrecoEstimado(itemDTO.getPrecoEstimado());
                    item.setPrecoReal(itemDTO.getPrecoReal());
                    item.setCategoria(categoria);
                    item.setPrioridade(itemDTO.getPrioridade());
                    item.setObservacoes(itemDTO.getObservacoes());

                    ItemLista itemAtualizado = itemListaRepository.save(item);
                    return ResponseEntity.ok(toDTO(itemAtualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/marcar-comprado")
    @Operation(summary = "Marcar item como comprado")
    public ResponseEntity<ItemListaDTO> marcarComoComprado(@PathVariable Long id) {
        return itemListaRepository.findById(id)
                .map(item -> {
                    item.marcarComoComprado();
                    ItemLista itemAtualizado = itemListaRepository.save(item);
                    return ResponseEntity.ok(toDTO(itemAtualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/marcar-nao-comprado")
    @Operation(summary = "Marcar item como não comprado")
    public ResponseEntity<ItemListaDTO> marcarComoNaoComprado(@PathVariable Long id) {
        return itemListaRepository.findById(id)
                .map(item -> {
                    item.marcarComoNaoComprado();
                    ItemLista itemAtualizado = itemListaRepository.save(item);
                    return ResponseEntity.ok(toDTO(itemAtualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover item da lista")
    public ResponseEntity<Void> removerItem(@PathVariable Long id) {
        if (itemListaRepository.existsById(id)) {
            itemListaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Método auxiliar para converter Entity para DTO
    private ItemListaDTO toDTO(ItemLista item) {
        ItemListaDTO dto = new ItemListaDTO();
        dto.setItemId(item.getItemId());
        dto.setListaId(item.getLista().getListaId());
        dto.setNomeProduto(item.getNomeProduto());
        dto.setQuantidade(item.getQuantidade());
        dto.setUnidadeMedida(item.getUnidadeMedida());
        dto.setPrecoEstimado(item.getPrecoEstimado());
        dto.setPrecoReal(item.getPrecoReal());
        dto.setComprado(item.getComprado());
        dto.setPrioridade(item.getPrioridade());
        dto.setObservacoes(item.getObservacoes());
        dto.setDataCriacao(item.getDataCriacao());
        dto.setDataCompra(item.getDataCompra());

        if (item.getProduto() != null) {
            dto.setProdutoId(item.getProduto().getProdutoId());
        }

        if (item.getCategoria() != null) {
            dto.setCategoriaId(item.getCategoria().getCategoriaId());
            dto.setCategoriaNome(item.getCategoria().getNome());
        }

        return dto;
    }
}