# Loja Compre Bem — API

API REST de produtos da Loja Compre Bem, consumida pelo app React Native (`loja-compre-bem`).

- Java 21 e Spring Boot 4.1.1
- Spring Data JPA com banco H2 em arquivo (`./data/db-api`, ignorado pelo git)
- Bean Validation nos dados de entrada e respostas de erro padronizadas
- Carga inicial com 67 produtos (`CargaInicial`)

## Como rodar

```bash
./mvnw spring-boot:run        # Windows: .\mvnw.cmd spring-boot:run
```

A API sobe em `http://localhost:8080`. Para o celular acessar, use o IP do computador na rede (ex.: `http://192.168.0.10:8080`).
Suba **uma instância só**: duas instâncias disputam o arquivo do banco e a porta 8080.

## Endpoints

| Método | Rota | Descrição |
|---|---|---|
| GET | `/api/v1/produtos/filtro?page=0&size=20&filtro=&sort=preco,asc` | Lista paginada. `filtro` busca em nome e descrição, sem diferenciar maiúsculas de minúsculas, e pode ficar vazio. Padrão: ordenada por nome. `sort` é opcional. |
| GET | `/api/v1/produtos/{id}` | Um produto pelo `produtoId` (UUID). |
| POST | `/api/v1/produtos` | Cadastra. Devolve `201` com o produto criado. |
| PUT | `/api/v1/produtos/{id}` | Edita. Devolve `200` com o produto atualizado. |
| DELETE | `/api/v1/produtos/{id}` | Remove. Devolve `204`. |

Corpo do `POST` e do `PUT`:

```json
{
  "nome": "Mesa de Escritório",
  "descricao": "Opcional, até 500 caracteres.",
  "preco": 399.90,
  "categoria": "Mesas",
  "imagem": "/img/mesa-industrial.png",
  "quantidade": 5
}
```

Obrigatórios: `nome` (até 100 caracteres), `preco` (maior que zero, até 2 casas decimais) e `quantidade` (inteiro, zero ou mais).

As imagens ficam em `/img/...` (ex.: `http://localhost:8080/img/mesa-industrial.png`). Só os três produtos originais (cadeira Sophy, mesa industrial e luminária LED) têm imagem; os demais vêm com `"imagem": null` e o app mostra uma imagem neutra de "sem imagem".

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
  "page": { "size": 20, "number": 0, "totalElements": 67, "totalPages": 4 }
}
```

A ordenação sempre usa o `id` como último critério, para que nenhum produto se repita ou some entre uma página e outra.

## Erros

Todos os erros têm o mesmo formato (`campos` só aparece na validação):

```json
{ "status": 400, "mensagem": "Verifique os campos informados.", "campos": { "preco": "O preço deve ser maior que zero." } }
```

| Situação | Status |
|---|---|
| Dados inválidos (campo obrigatório, preço ≤ 0, JSON malformado) | 400 |
| `id` que não é um UUID, ou `sort` com campo inexistente | 400 |
| Produto não encontrado (GET, PUT, DELETE) | 404 |
| Erro não previsto (sem detalhes técnicos na resposta) | 500 |

## Carga inicial e quantidade de produtos

A cada inicialização a `CargaInicial` insere só os produtos cujo nome ainda não existe, então pode rodar várias vezes sem duplicar e completa um banco que já tinha alguns produtos. O log mostra `Carga inicial: N produto(s) inserido(s); total no banco: M`.

Para conferir: abra `http://localhost:8080/api/v1/produtos/filtro?size=1` e veja `page.totalElements`.

## Restaurar os produtos de exemplo

Com a API parada, apague a pasta `data/` e suba de novo.
