package br.com.comprebem.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "TB_PRODUTO")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    private String categoria;

    private String imagem; // caminho servido pela própria API, ex.: /img/mesa-industrial.png

    @Column(nullable = false)
    private Integer quantidade;

    protected Produto() {
    }

    public Produto(String nome, String descricao, BigDecimal preco, String categoria, String imagem, Integer quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.imagem = imagem;
        this.quantidade = quantidade;
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public BigDecimal getPreco() { return preco; }
    public String getCategoria() { return categoria; }
    public String getImagem() { return imagem; }
    public Integer getQuantidade() { return quantidade; }
}
