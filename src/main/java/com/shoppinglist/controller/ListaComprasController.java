package com.shoppinglist.controller;

import com.shoppinglist.dto.ListaComprasDTO;
import com.shoppinglist.model.ItemLista;
import com.shoppinglist.model.ListaCompras;
import com.shoppinglist.model.Usuario;
import com.shoppinglist.repository.ListaComprasRepository;
import com.shoppinglist.repository.UsuarioRepository;
import com.shoppinglist.service.ListaComprasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/listas-compras")
@Tag(name = "Listas de Compras", description = "Operações relacionadas a listas de compras")
public class ListaComprasController {

    @Autowired
    private ListaComprasRepository listaComprasRepository;

    @Autowired
    private ListaComprasService listaComprasService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    @Operation(summary = "Listar todas as listas de compras")
    public ResponseEntity<List<ListaComprasDTO>> listarListas() {
        List<ListaCompras> listas = listaComprasRepository.findAll();
        List<ListaComprasDTO> listasDTO = listas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listasDTO);
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar listas de compras por usuário")
    public ResponseEntity<List<ListaComprasDTO>> listarListasPorUsuario(@PathVariable Long usuarioId) {
        List<ListaCompras> listas = listaComprasRepository.findByUsuarioUsuarioId(usuarioId);
        List<ListaComprasDTO> listasDTO = listas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listasDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar lista de compras por ID")
    public ResponseEntity<ListaComprasDTO> buscarLista(@PathVariable Long id) {
        return listaComprasRepository.findById(id)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar uma nova lista de compras")
    public ResponseEntity<ListaComprasDTO> criarLista(@RequestBody ListaComprasDTO listaDTO) {
        Usuario usuario = usuarioRepository.findById(listaDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        ListaCompras lista = new ListaCompras();
        lista.setNome(listaDTO.getNome());
        lista.setDescricao(listaDTO.getDescricao());
        lista.setDataCompraPrevista(listaDTO.getDataCompraPrevista());
        lista.setOrcamentoTotal(listaDTO.getOrcamentoTotal());
        lista.setNotificarAntes(listaDTO.getNotificarAntes());
        lista.setUsuario(usuario);

        ListaCompras listaSalva = listaComprasRepository.save(lista);
        return ResponseEntity.ok(toDTO(listaSalva));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma lista de compras existente")
    public ResponseEntity<ListaComprasDTO> atualizarLista(@PathVariable Long id, @RequestBody ListaComprasDTO listaDTO) {
        return listaComprasRepository.findById(id)
                .map(lista -> {
                    lista.setNome(listaDTO.getNome());
                    lista.setDescricao(listaDTO.getDescricao());
                    lista.setDataCompraPrevista(listaDTO.getDataCompraPrevista());
                    lista.setOrcamentoTotal(listaDTO.getOrcamentoTotal());
                    lista.setNotificarAntes(listaDTO.getNotificarAntes());
                    lista.setStatus(listaDTO.getStatus());

                    ListaCompras listaAtualizada = listaComprasRepository.save(lista);
                    return ResponseEntity.ok(toDTO(listaAtualizada));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma lista de compras")
    public ResponseEntity<Void> excluirLista(@PathVariable Long id) {
        if (listaComprasRepository.existsById(id)) {
            listaComprasRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Método auxiliar para converter Entity para DTO
    private ListaComprasDTO toDTO(ListaCompras lista) {
        ListaComprasDTO dto = new ListaComprasDTO();
        dto.setListaId(lista.getListaId());
        dto.setNome(lista.getNome());
        dto.setDescricao(lista.getDescricao());
        dto.setDataCompraPrevista(lista.getDataCompraPrevista());
        dto.setDataCompraRealizada(lista.getDataCompraRealizada());
        dto.setOrcamentoTotal(lista.getOrcamentoTotal());
        dto.setValorTotalGasto(lista.getValorTotalGasto());
        dto.setStatus(lista.getStatus());
        dto.setNotificarAntes(lista.getNotificarAntes());
        dto.setDataCriacao(lista.getDataCriacao());
        dto.setDataAtualizacao(lista.getDataAtualizacao());

        if (lista.getUsuario() != null) {
            dto.setUsuarioId(lista.getUsuario().getUsuarioId());
        }

        return dto;
    }


    @PutMapping("/{id}/finalizar")
    @Operation(summary = "Finalizar lista de compras")
    public ResponseEntity<ListaCompras> finalizarLista(
            @PathVariable Long id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataCompra) {

        try {
            ListaCompras listaFinalizada = listaComprasService.finalizarLista(id, dataCompra);
            return ResponseEntity.ok(listaFinalizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Atualizar preço real de um item
    @PutMapping("/itens/{itemId}/preco-real")
    public ResponseEntity<ItemLista> atualizarPrecoReal(
            @PathVariable Long itemId,
            @RequestBody BigDecimal precoReal) {
        try {
            ItemLista itemAtualizado = listaComprasService.atualizarPrecoReal(itemId, precoReal);
            return ResponseEntity.ok(itemAtualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}