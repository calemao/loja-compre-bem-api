package br.com.comprebem.api.config;

import br.com.comprebem.api.entity.Produto;
import br.com.comprebem.api.repository.ProdutoRepository;
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
                "Cadeira ergonômica com ajuste de altura e apoio lombar.",
                new BigDecimal("2799.00"), "Cadeiras", "/img/Cadeira-ergonomica-Sophy-Azul.png", 10));
        repository.save(new Produto("Mesa Industrial Grande Escritório Reunião Home Office 190x80",
                "Mesa grande em estilo industrial para escritório ou home office.",
                new BigDecimal("655.80"), "Mesas", "/img/mesa-industrial.png", 5));
        repository.save(new Produto("Luminária Led Com Braço Oscilante Longo",
                "Luminária de mesa em LED com braço oscilante longo.",
                new BigDecimal("79.90"), "Iluminação", "/img/luminaria-led.png", 15));
    }
}
