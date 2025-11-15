package com.shoppinglist.repository;

import com.shoppinglist.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByAtivo(Character ativo); // MUDADO: Character em vez de String
    List<Categoria> findByUsuarioUsuarioId(Long usuarioId);
}