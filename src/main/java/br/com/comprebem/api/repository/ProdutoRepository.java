package br.com.comprebem.api.repository;

import br.com.comprebem.api.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    @Query("""
            SELECT p FROM Produto p
            WHERE UPPER(p.nome) LIKE CONCAT('%', :filtro, '%')
               OR UPPER(p.descricao) LIKE CONCAT('%', :filtro, '%')
            """)
    Page<Produto> findByFiltro(@Param("filtro") String filtro, Pageable pageable);

    boolean existsByNome(String nome);

    // Remove a imagem de um produto da carga inicial que não deve ter foto (só se ainda tiver uma).
    @Modifying
    @Transactional
    @Query("UPDATE Produto p SET p.imagem = NULL WHERE p.nome = :nome AND p.imagem IS NOT NULL")
    int desvincularImagem(@Param("nome") String nome);
}
