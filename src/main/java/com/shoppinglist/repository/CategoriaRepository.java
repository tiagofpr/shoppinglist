package com.shoppinglist.repository;

import com.shoppinglist.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByAtivo(String ativo);
    List<Categoria> findByUsuarioUsuarioId(Long usuarioId);
}
