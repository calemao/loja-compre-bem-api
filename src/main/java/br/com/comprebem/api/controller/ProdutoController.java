package br.com.comprebem.api.controller;

import br.com.comprebem.api.dto.ProdutoRequestDTO;
import br.com.comprebem.api.dto.ProdutoResponseDTO;
import br.com.comprebem.api.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // GET /api/v1/produtos/filtro?page=0&size=20&filtro=&sort=preco,asc
    //   ->  { content: [...], page: { size, number, totalElements, totalPages } }
    @GetMapping("/filtro")
    public Page<ProdutoResponseDTO> findByFiltro(
            @RequestParam(defaultValue = "") String filtro,
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        return produtoService.findByFiltro(filtro, pageable);
    }

    @GetMapping("/{id}")
    public ProdutoResponseDTO findById(@PathVariable("id") UUID id) {
        return produtoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponseDTO save(@Valid @RequestBody ProdutoRequestDTO produto) {
        return produtoService.save(produto);
    }

    @PutMapping("/{id}")
    public ProdutoResponseDTO update(@PathVariable("id") UUID id, @Valid @RequestBody ProdutoRequestDTO produto) {
        return produtoService.update(id, produto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") UUID id) {
        produtoService.delete(id);
    }
}
