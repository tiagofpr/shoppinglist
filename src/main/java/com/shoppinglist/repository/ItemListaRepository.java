package com.shoppinglist.repository;

import com.shoppinglist.model.ItemLista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemListaRepository extends JpaRepository<ItemLista, Long> {
    List<ItemLista> findByListaListaId(Long listaId);
    List<ItemLista> findByListaListaIdAndComprado(Long listaId, String comprado);
    List<ItemLista> findByComprado(String comprado);

    @Query("SELECT i FROM ItemLista i WHERE i.lista.usuario.usuarioId = :usuarioId AND i.comprado = 'N'")
    List<ItemLista> findItensPendentesPorUsuario(Long usuarioId);

    @Modifying
    @Query("UPDATE ItemLista i SET i.comprado = 'S', i.dataCompra = CURRENT_TIMESTAMP WHERE i.itemId = :itemId")
    void marcarComoComprado(Long itemId);

    @Modifying
    @Query("UPDATE ItemLista i SET i.comprado = 'N', i.dataCompra = null WHERE i.itemId = :itemId")
    void marcarComoNaoComprado(Long itemId);

    long countByListaListaIdAndComprado(Long listaId, String comprado);
}
