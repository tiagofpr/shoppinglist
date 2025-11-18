package com.shoppinglist.service;

import com.shoppinglist.model.ItemLista;
import com.shoppinglist.model.ListaCompras;
import com.shoppinglist.repository.ItemListaRepository;
import com.shoppinglist.repository.ListaComprasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ListaComprasService {

    @Autowired
    private ListaComprasRepository listaComprasRepository;

    @Autowired
    private ItemListaRepository itemListaRepository;

    @Transactional
    public ListaCompras finalizarLista(Long listaId, LocalDate dataCompra) {
        ListaCompras lista = listaComprasRepository.findByIdWithItens(listaId)
                .orElseThrow(() -> new RuntimeException("Lista não encontrada"));

        // Calcular valor total gasto baseado nos itens comprados
        BigDecimal valorTotalGasto = BigDecimal.ZERO;

        for (ItemLista item : lista.getItens()) {
            if (item.getComprado() == 'S' && item.getPrecoReal() != null) {
                BigDecimal quantidade = item.getQuantidade() != null ? item.getQuantidade() : BigDecimal.ONE;
                BigDecimal precoReal = item.getPrecoReal();
                valorTotalGasto = valorTotalGasto.add(precoReal.multiply(quantidade));
            }
        }

        // Atualizar a lista
        lista.setValorTotalGasto(valorTotalGasto);
        lista.setDataCompraRealizada(dataCompra != null ? dataCompra : LocalDate.now());
        lista.setStatus("CONCLUIDA");

        return listaComprasRepository.save(lista);
    }
    // Método para atualizar preço real de um item
    @Transactional
    public ItemLista atualizarPrecoReal(Long itemId, BigDecimal precoReal) {
        ItemLista item = itemListaRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        item.setPrecoReal(precoReal);
        item.setDataAtualizacao(LocalDateTime.now());

        return itemListaRepository.save(item);
    }
}
