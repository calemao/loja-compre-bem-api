# Loja Compre Bem — API

API REST de produtos da Loja Compre Bem, consumida pelo app React Native (`loja-compre-bem`).

- Java 21 e Spring Boot 4.1.1
- Spring Data JPA com banco H2 em arquivo (`./data/db-api`, ignorado pelo git)
- Na primeira execução são criados 3 produtos de exemplo (`CargaInicial`)

## Como rodar

```bash
./mvnw spring-boot:run        # Windows: .\mvnw.cmd spring-boot:run
```

A API sobe em `http://localhost:8080`. Para o celular acessar, use o IP do computador na rede (ex.: `http://192.168.0.10:8080`).
Suba **uma instância só**: duas instâncias disputam o arquivo do banco e a porta 8080.

## Endpoints

| Método | Rota | Descrição |
|---|---|---|
| GET | `/api/v1/produtos/filtro?page=0&size=20&filtro=` | Lista paginada. `filtro` busca em nome e descrição, sem diferenciar maiúsculas de minúsculas, e pode ficar vazio (devolve tudo). Ordenada por nome. |
| GET | `/api/v1/produtos/{id}` | Um produto pelo `produtoId` (UUID). `404` se não existir. |
| DELETE | `/api/v1/produtos/{id}` | Remove o produto. `204` em caso de sucesso, `404` se não existir. |

As imagens ficam em `/img/...` (ex.: `http://localhost:8080/img/mesa-industrial.png`).

## Formato da resposta paginada

`spring.data.web.pageable.serialization-mode=via_dto` mantém o formato estável:

```json
{
  "content": [
    {
      "produtoId": "df06703a-888b-4274-b5ac-847099d8b4c9",
      "nome": "Cadeira Ergonômica Elements Sophy – Preto & Azul",
      "descricao": "Cadeira ergonômica com ajuste de altura e apoio lombar.",
      "preco": 2799.00,
      "categoria": "Cadeiras",
      "imagem": "/img/Cadeira-ergonomica-Sophy-Azul.png",
      "quantidade": 10
    }
  ],
  "page": { "size": 20, "number": 0, "totalElements": 3, "totalPages": 1 }
}
```

## Restaurar os produtos de exemplo

Com a API parada, apague a pasta `data/` e suba de novo.
