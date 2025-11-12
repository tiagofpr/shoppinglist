package com.shoppinglist.repository;

import com.shoppinglist.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByAtivo(String ativo);
    List<Produto> findByCategoriaCategoriaId(Long categoriaId);
    List<Produto> findByNomeContainingIgnoreCase(String nome);
    Optional<Produto> findByNomeAndMarca(String nome, String marca);

    @Query("SELECT p FROM Produto p WHERE p.categoria.nome = :categoriaNome")
    List<Produto> findByCategoriaNome(String categoriaNome);
}