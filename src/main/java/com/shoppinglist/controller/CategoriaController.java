package com.shoppinglist.controller;

import com.shoppinglist.dto.CategoriaDTO;
import com.shoppinglist.model.Categoria;
import com.shoppinglist.repository.CategoriaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorias", description = "Operações relacionadas a categorias de produtos")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    @Operation(summary = "Listar todas as categorias ativas")
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        List<Categoria> categorias = categoriaRepository.findByAtivo("S");
        List<CategoriaDTO> categoriasDTO = categorias.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoriasDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID")
    public ResponseEntity<CategoriaDTO> buscarCategoria(@PathVariable Long id) {
        return categoriaRepository.findById(id)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar uma nova categoria")
    public ResponseEntity<CategoriaDTO> criarCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaDTO.getNome());
        categoria.setDescricao(categoriaDTO.getDescricao());
        categoria.setCor(categoriaDTO.getCor());
        categoria.setIcone(categoriaDTO.getIcone());

        Categoria categoriaSalva = categoriaRepository.save(categoria);
        return ResponseEntity.ok(toDTO(categoriaSalva));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma categoria existente")
    public ResponseEntity<CategoriaDTO> atualizarCategoria(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
        return categoriaRepository.findById(id)
                .map(categoria -> {
                    categoria.setNome(categoriaDTO.getNome());
                    categoria.setDescricao(categoriaDTO.getDescricao());
                    categoria.setCor(categoriaDTO.getCor());
                    categoria.setIcone(categoriaDTO.getIcone());
                    Categoria categoriaAtualizada = categoriaRepository.save(categoria);
                    return ResponseEntity.ok(toDTO(categoriaAtualizada));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma categoria (desativação lógica)")
    public ResponseEntity<Void> excluirCategoria(@PathVariable Long id) {
        return categoriaRepository.findById(id)
                .map(categoria -> {
                    categoria.setAtivo('N');
                    categoriaRepository.save(categoria);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Método auxiliar para converter Entity para DTO
    private CategoriaDTO toDTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setCategoriaId(categoria.getCategoriaId());
        dto.setNome(categoria.getNome());
        dto.setDescricao(categoria.getDescricao());
        dto.setCor(categoria.getCor());
        dto.setIcone(categoria.getIcone());
        dto.setDataCriacao(categoria.getDataCriacao());
        dto.setAtivo(categoria.getAtivo());

        if (categoria.getUsuario() != null) {
            dto.setUsuarioId(categoria.getUsuario().getUsuarioId());
        }

        return dto;
    }
}