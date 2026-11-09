package br.com.comprebem.api.config;

import br.com.comprebem.api.entity.Produto;
import br.com.comprebem.api.repository.ProdutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Carga inicial de produtos. Roda a cada inicialização, mas só insere o produto cujo nome
 * ainda não existe no banco: pode ser executada várias vezes sem duplicar, e completa
 * um banco que já tinha só alguns produtos.
 */
@Component
public class CargaInicial implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(CargaInicial.class);

    // Só os três produtos originais têm foto de verdade no servidor; os demais ficam sem imagem
    // e o app mostra uma imagem neutra de "sem imagem".
    private static final Map<String, String> IMAGEM_POR_NOME = Map.of(
            "Cadeira Ergonômica Elements Sophy – Preto & Azul", "/img/Cadeira-ergonomica-Sophy-Azul.png",
            "Mesa Industrial Grande Escritório Reunião Home Office 190x80", "/img/mesa-industrial.png",
            "Luminária Led Com Braço Oscilante Longo", "/img/luminaria-led.png"
    );

    private final ProdutoRepository repository;

    public CargaInicial(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        int inseridos = 0;
        int desvinculados = 0;
        for (Produto produto : PRODUTOS) {
            if (!repository.existsByNome(produto.getNome())) {
                repository.save(produto);
                inseridos++;
            } else if (produto.getImagem() == null) {
                // Banco criado antes: tira a foto que foi gravada em produtos que não são dela.
                desvinculados += repository.desvincularImagem(produto.getNome());
            }
        }
        log.info("Carga inicial: {} produto(s) inserido(s); {} imagem(ns) desvinculada(s); total no banco: {}",
                inseridos, desvinculados, repository.count());
    }

    private static Produto item(String nome, String descricao, String preco, String categoria, int quantidade) {
        return new Produto(nome, descricao, new BigDecimal(preco), categoria,
                IMAGEM_POR_NOME.get(nome), quantidade);
    }

    private static final List<Produto> PRODUTOS = List.of(
            item("Cadeira Ergonômica Elements Sophy – Preto & Azul",
                    "Cadeira ergonômica com ajuste de altura e apoio lombar.",
                    "2799.00", "Cadeiras", 10),
            item("Mesa Industrial Grande Escritório Reunião Home Office 190x80",
                    "Mesa grande em estilo industrial para escritório ou home office.",
                    "655.80", "Mesas", 5),
            item("Luminária Led Com Braço Oscilante Longo",
                    "Luminária de mesa em LED com braço oscilante longo.",
                    "79.90", "Iluminação", 15),
            item("Cadeira Presidente Giratória Courino Preta",
                    "Cadeira presidente com assento acolchoado e base cromada.",
                    "899.90", "Cadeiras", 12),
            item("Cadeira Gamer Reclinável 180 Graus",
                    "Cadeira gamer com apoio de braço 3D e almofadas de pescoço e lombar.",
                    "1349.00", "Cadeiras", 8),
            item("Cadeira Secretária Giratória Tela Mesh",
                    "Encosto em tela respirável e regulagem de altura a gás.",
                    "429.90", "Cadeiras", 20),
            item("Cadeira Fixa Interlocutor Empilhável",
                    "Cadeira fixa para recepção e salas de reunião, estrutura em aço.",
                    "259.00", "Cadeiras", 25),
            item("Banqueta Alta Estofada Aço Dourado",
                    "Banqueta alta com assento estofado e apoio para os pés.",
                    "189.90", "Cadeiras", 18),
            item("Cadeira de Escritório Diretor Couro Sintético",
                    "Cadeira diretor com mecanismo relax e braços fixos.",
                    "1199.00", "Cadeiras", 6),
            item("Poltrona Decorativa Pé Palito Cinza",
                    "Poltrona de sala com pés em madeira e tecido suede.",
                    "579.90", "Cadeiras", 9),
            item("Cadeira Dobrável Reforçada Preta",
                    "Cadeira dobrável de aço para eventos, suporta até 120 kg.",
                    "99.90", "Cadeiras", 40),
            item("Mesa de Escritório em L Home Office 150x150",
                    "Mesa em L com gaveteiro e passa-cabos.",
                    "789.00", "Mesas", 7),
            item("Mesa Digitadora com Suporte para Teclado",
                    "Mesa compacta com bandeja retrátil para teclado.",
                    "349.90", "Mesas", 14),
            item("Mesa de Jantar Retangular 6 Lugares Madeira Maciça",
                    "Tampo em madeira maciça com acabamento em verniz fosco.",
                    "1899.00", "Mesas", 4),
            item("Mesa Dobrável Multiuso 120x60",
                    "Mesa dobrável leve para trabalho ou estudo em qualquer cômodo.",
                    "279.90", "Mesas", 16),
            item("Escrivaninha Infantil com Gaveta",
                    "Escrivaninha para quarto infantil com gaveta e prateleira.",
                    "399.00", "Mesas", 11),
            item("Mesa de Centro Redonda Estilo Nórdico",
                    "Mesa de centro com tampo de vidro e pés em madeira.",
                    "459.90", "Mesas", 10),
            item("Mesa Regulável em Altura Elétrica 140x70",
                    "Mesa com motor duplo e memória de três alturas.",
                    "2399.00", "Mesas", 3),
            item("Mesa de Reunião Oval 8 Lugares",
                    "Mesa oval para sala de reunião com tampo de 25 mm.",
                    "2199.00", "Mesas", 2),
            item("Luminária de Mesa Articulada Preta E27",
                    "Luminária com braço articulado e base com clip.",
                    "69.90", "Iluminação", 22),
            item("Fita LED RGB 5 Metros com Controle",
                    "Fita LED autoadesiva com controle remoto e 16 cores.",
                    "59.90", "Iluminação", 35),
            item("Pendente Aramado Industrial Preto",
                    "Pendente em aramado para mesas de jantar e balcões.",
                    "139.90", "Iluminação", 13),
            item("Abajur de Cabeceira Cúpula Branca",
                    "Abajur com base em madeira e cúpula de tecido.",
                    "89.90", "Iluminação", 17),
            item("Plafon LED Quadrado 24W Embutir",
                    "Plafon de embutir com luz branca neutra.",
                    "49.90", "Iluminação", 50),
            item("Luminária de Piso Arco Cobre",
                    "Luminária de piso em arco com base de mármore sintético.",
                    "499.00", "Iluminação", 5),
            item("Refletor LED 50W Bivolt IP66",
                    "Refletor à prova d'água para áreas externas.",
                    "79.00", "Iluminação", 30),
            item("Luminária Spot Trilho Direcionável Preta",
                    "Spot para trilho eletrificado com giro de 330 graus.",
                    "64.90", "Iluminação", 28),
            item("Estante Livreiro 5 Prateleiras Branca",
                    "Estante com 5 prateleiras e acabamento em MDP.",
                    "329.00", "Estantes e Armários", 12),
            item("Armário Multiuso 2 Portas Cinza",
                    "Armário de escritório com chave e duas prateleiras internas.",
                    "519.90", "Estantes e Armários", 8),
            item("Estante Industrial Ferro e Madeira 4 Nichos",
                    "Estante com estrutura de ferro e prateleiras em madeira.",
                    "449.00", "Estantes e Armários", 9),
            item("Gaveteiro Volante 3 Gavetas com Rodízios",
                    "Gaveteiro com chave e rodízios para encaixar sob a mesa.",
                    "379.90", "Estantes e Armários", 15),
            item("Arquivo de Aço 4 Gavetas para Pastas Suspensas",
                    "Arquivo em aço com trilho telescópico e chave.",
                    "899.00", "Estantes e Armários", 4),
            item("Rack para TV até 55 Polegadas",
                    "Rack com nichos e passa-fio para home theater.",
                    "429.90", "Estantes e Armários", 10),
            item("Sapateira Slim 3 Portas Basculantes",
                    "Sapateira estreita com capacidade para 18 pares.",
                    "259.00", "Estantes e Armários", 14),
            item("Prateleira Nicho Quadrado Kit com 3",
                    "Kit de 3 nichos decorativos para parede.",
                    "99.90", "Estantes e Armários", 33),
            item("Teclado Mecânico ABNT2 Switch Azul",
                    "Teclado mecânico com layout ABNT2 e iluminação branca.",
                    "249.90", "Informática", 19),
            item("Mouse Sem Fio Ergonômico 1600 DPI",
                    "Mouse sem fio com receptor USB e 6 botões.",
                    "79.90", "Informática", 45),
            item("Monitor LED 24 Polegadas Full HD",
                    "Monitor com painel IPS, 75 Hz e entradas HDMI e VGA.",
                    "699.00", "Informática", 12),
            item("Webcam Full HD 1080p com Microfone",
                    "Webcam USB com foco automático e microfone embutido.",
                    "189.90", "Informática", 21),
            item("Headset USB com Cancelamento de Ruído",
                    "Headset com microfone flexível e almofadas macias.",
                    "159.90", "Informática", 26),
            item("Suporte para Notebook Ajustável Alumínio",
                    "Suporte dobrável com seis níveis de altura.",
                    "99.90", "Informática", 31),
            item("Hub USB 3.0 com 4 Portas",
                    "Hub compacto com cabo de 30 cm.",
                    "54.90", "Informática", 40),
            item("SSD 480 GB SATA 2,5 Polegadas",
                    "SSD com leitura de até 500 MB/s.",
                    "229.00", "Informática", 23),
            item("Quadro Decorativo Abstrato 60x90",
                    "Quadro com moldura preta e vidro.",
                    "189.00", "Decoração", 15),
            item("Vaso de Cerâmica Fosco Branco Médio",
                    "Vaso decorativo em cerâmica com acabamento fosco.",
                    "69.90", "Decoração", 27),
            item("Relógio de Parede Silencioso 30 cm",
                    "Relógio com mecanismo silencioso e moldura metálica.",
                    "79.90", "Decoração", 24),
            item("Espelho Redondo com Moldura Metálica 60 cm",
                    "Espelho com moldura dourada para sala ou corredor.",
                    "249.90", "Decoração", 11),
            item("Tapete Sala Felpudo 2,00 x 1,50",
                    "Tapete macio antiderrapante em poliéster.",
                    "329.00", "Decoração", 7),
            item("Almofada Decorativa Veludo 45x45",
                    "Almofada com enchimento de fibra siliconada.",
                    "44.90", "Decoração", 60),
            item("Cortina Blackout 2,80 x 2,30",
                    "Cortina blackout com ilhós e tecido corta-luz.",
                    "139.90", "Decoração", 18),
            item("Kit 3 Porta-Retratos Madeira",
                    "Kit de porta-retratos para mesa ou parede.",
                    "59.90", "Decoração", 36),
            item("Organizador de Mesa Multifuncional Tela Aramada",
                    "Organizador com porta-canetas, papéis e celular.",
                    "49.90", "Organização", 42),
            item("Caixa Organizadora Plástica 30 Litros",
                    "Caixa com tampa e travas laterais.",
                    "39.90", "Organização", 55),
            item("Kit 6 Cabides de Veludo Slim",
                    "Cabides antiderrapantes que economizam espaço.",
                    "34.90", "Organização", 70),
            item("Organizador de Cabos Velcro Kit com 20",
                    "Fitas de velcro reutilizáveis para fios.",
                    "19.90", "Organização", 90),
            item("Cesto Organizador Dobrável Tecido",
                    "Cesto com alças para roupas e brinquedos.",
                    "44.90", "Organização", 38),
            item("Porta-Documentos Acrílico A4 3 Níveis",
                    "Bandeja de acrílico para organizar documentos.",
                    "64.90", "Organização", 20),
            item("Quadro de Avisos Magnético 60x40",
                    "Quadro branco magnético com moldura de alumínio.",
                    "89.90", "Organização", 16),
            item("Lixeira de Escritório Inox 12 Litros",
                    "Lixeira com tampa vai e vem.",
                    "59.90", "Organização", 29),
            item("Agenda Executiva 2027 Capa Dura",
                    "Agenda diária com marcador de página.",
                    "54.90", "Papelaria", 44),
            item("Caderno Universitário 10 Matérias 200 Folhas",
                    "Caderno com capa dura e divisórias.",
                    "29.90", "Papelaria", 80),
            item("Caneta Esferográfica Azul Caixa com 50",
                    "Caneta com ponta de 1,0 mm e escrita macia.",
                    "39.90", "Papelaria", 65),
            item("Grampeador de Mesa 26/6 até 25 Folhas",
                    "Grampeador metálico com trava.",
                    "24.90", "Papelaria", 48),
            item("Calculadora de Mesa 12 Dígitos",
                    "Calculadora com visor grande e solar.",
                    "34.90", "Papelaria", 37),
            item("Post-it Colorido Bloco 76x76 Pack com 12",
                    "Blocos autoadesivos em cores neon.",
                    "42.90", "Papelaria", 52),
            item("Mochila para Notebook 15,6 com Porta USB",
                    "Mochila impermeável com compartimento acolchoado.",
                    "149.90", "Papelaria", 19),
            item("Fragmentadora de Papel 8 Folhas",
                    "Fragmentadora com cesto de 15 litros.",
                    "329.00", "Papelaria", 6)
    );
}
