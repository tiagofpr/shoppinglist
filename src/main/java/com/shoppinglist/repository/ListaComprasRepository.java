package com.shoppinglist.repository;

import com.shoppinglist.model.ListaCompras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ListaComprasRepository extends JpaRepository<ListaCompras, Long> {
    List<ListaCompras> findByUsuarioUsuarioId(Long usuarioId);

    List<ListaCompras> findByStatus(String status);

    List<ListaCompras> findByUsuarioUsuarioIdAndStatus(Long usuarioId, String status);

    @Query("SELECT DISTINCT l FROM ListaCompras l LEFT JOIN FETCH l.itens WHERE l.listaId = :listaId")
    Optional<ListaCompras> findByIdWithItens(@Param("listaId") Long listaId);

    @Query("SELECT l FROM ListaCompras l WHERE l.usuario.usuarioId = :usuarioId AND l.dataCompraPrevista BETWEEN :startDate AND :endDate")
    List<ListaCompras> findByUsuarioAndDataCompraPrevistaBetween(Long usuarioId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT l FROM ListaCompras l WHERE l.dataCompraPrevista <= :date AND l.status = 'PENDENTE'")
    List<ListaCompras> findListasParaNotificar(LocalDate date);

    List<ListaCompras> findByDataCompraPrevistaAndStatus(LocalDate dataCompraPrevista, String status);

    // MODIFIQUE O MÉTODO EXISTENTE PARA CARREGAR OS ITENS
    @Query("SELECT DISTINCT l FROM ListaCompras l LEFT JOIN FETCH l.itens WHERE l.dataCompraRealizada BETWEEN :dataInicio AND :dataFim AND l.status = :status")
    List<ListaCompras> findByDataCompraRealizadaBetweenAndStatus(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("status") String status
    );

    // Método para buscar listas concluídas por período CARREGANDO OS ITENS
    @Query("SELECT DISTINCT l FROM ListaCompras l LEFT JOIN FETCH l.itens WHERE l.dataCompraRealizada BETWEEN :dataInicio AND :dataFim AND l.status = :status")
    List<ListaCompras> findByDataCompraRealizadaBetweenAndStatusComItens(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("status") String status
    );
}