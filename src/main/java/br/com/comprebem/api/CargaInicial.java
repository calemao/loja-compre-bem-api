package br.com.comprebem.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CargaInicial implements CommandLineRunner {

    private final ProdutoRepository repository;

    public CargaInicial(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) return;

        repository.save(new Produto("Cadeira Ergonômica Elements Sophy – Preto & Azul",
                new BigDecimal("2799.00"), 10, "/img/Cadeira-ergonomica-Sophy-Azul.png"));
        repository.save(new Produto("Mesa Industrial Grande Escritório Reunião Home Office 190x80",
                new BigDecimal("655.80"), 5, "/img/mesa-industrial.png"));
        repository.save(new Produto("Luminária Led Com Braço Oscilante Longo",
                new BigDecimal("79.90"), 15, "/img/luminaria-led.png"));
    }
}
