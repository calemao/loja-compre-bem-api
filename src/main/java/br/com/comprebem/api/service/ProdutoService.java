package br.com.comprebem.api.service;

import br.com.comprebem.api.dto.ProdutoRequestDTO;
import br.com.comprebem.api.dto.ProdutoResponseDTO;
import br.com.comprebem.api.entity.Produto;
import br.com.comprebem.api.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    // O id entra como último critério de ordenação: sem ele, produtos com o mesmo nome (ou preço)
    // poderiam repetir ou sumir entre uma página e outra no scroll infinito.
    public Page<ProdutoResponseDTO> findByFiltro(String filtro, Pageable pageable) {
        Pageable estavel = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                pageable.getSort().and(Sort.by("id")));
        return produtoRepository.findByFiltro(filtro.trim().toUpperCase(Locale.ROOT), estavel)
                .map(ProdutoResponseDTO::toDto);
    }

    public ProdutoResponseDTO findById(UUID id) {
        return ProdutoResponseDTO.toDto(buscarOuFalhar(id));
    }

    public ProdutoResponseDTO save(ProdutoRequestDTO dto) {
        Produto produto = new Produto(dto.nome().trim(), dto.descricao(), dto.preco(),
                dto.categoria(), dto.imagem(), dto.quantidade());
        return ProdutoResponseDTO.toDto(produtoRepository.save(produto));
    }

    public ProdutoResponseDTO update(UUID id, ProdutoRequestDTO dto) {
        Produto produto = buscarOuFalhar(id);
        produto.atualizar(dto.nome().trim(), dto.descricao(), dto.preco(),
                dto.categoria(), dto.imagem(), dto.quantidade());
        return ProdutoResponseDTO.toDto(produtoRepository.save(produto));
    }

    public void delete(UUID id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado.");
        }
        produtoRepository.deleteById(id);
    }

    private Produto buscarOuFalhar(UUID id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }
}
