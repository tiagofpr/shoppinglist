package com.shoppinglist.repository;

import com.shoppinglist.model.ListaCompras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ListaComprasRepository extends JpaRepository<ListaCompras, Long> {
    List<ListaCompras> findByUsuarioUsuarioId(Long usuarioId);
    List<ListaCompras> findByStatus(String status);
    List<ListaCompras> findByUsuarioUsuarioIdAndStatus(Long usuarioId, String status);

    @Query("SELECT l FROM ListaCompras l WHERE l.usuario.usuarioId = :usuarioId AND l.dataCompraPrevista BETWEEN :startDate AND :endDate")
    List<ListaCompras> findByUsuarioAndDataCompraPrevistaBetween(Long usuarioId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT l FROM ListaCompras l WHERE l.dataCompraPrevista <= :date AND l.status = 'PENDENTE'")
    List<ListaCompras> findListasParaNotificar(LocalDate date);

    List<ListaCompras> findByDataCompraPrevistaAndStatus(LocalDate dataCompraPrevista, String status);
}