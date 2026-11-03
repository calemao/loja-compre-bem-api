package br.com.comprebem.api.service;

import br.com.comprebem.api.dto.ProdutoResponseDTO;
import br.com.comprebem.api.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;
import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // O filtro vai em maiúsculas porque a consulta compara com UPPER(nome) e UPPER(descricao),
    // então a busca não diferencia maiúsculas de minúsculas. Filtro vazio devolve tudo.
    public Page<ProdutoResponseDTO> findByFiltro(String filtro, Pageable pageable) {
        return produtoRepository.findByFiltro(filtro.trim().toUpperCase(Locale.ROOT), pageable)
                .map(ProdutoResponseDTO::toDto);
    }

    public ProdutoResponseDTO findById(UUID id) {
        return produtoRepository.findById(id)
                .map(ProdutoResponseDTO::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    public void delete(UUID id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }
}
