package br.com.comprebem.api.repository;

import br.com.comprebem.api.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    @Query("""
            SELECT p FROM Produto p
            WHERE UPPER(p.nome) LIKE CONCAT('%', :filtro, '%')
               OR UPPER(p.descricao) LIKE CONCAT('%', :filtro, '%')
            """)
    Page<Produto> findByFiltro(@Param("filtro") String filtro, Pageable pageable);
}
