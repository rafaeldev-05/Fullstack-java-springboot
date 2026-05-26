# FilaSolidaria

FilaSolidaria e um projeto fullstack simples para gerenciar uma fila virtual de atendimento. Ele resolve uma dor comum do dia a dia: reduzir a espera presencial em filas de clinicas, barbearias, assistencias tecnicas, secretarias e outros servicos.

## Tecnologias

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Bean Validation
- Banco H2 em memoria
- Maven
- HTML, CSS e JavaScript puro

## Conceitos de orientacao a objetos usados

- `PessoaFila`: classe/entidade que representa uma pessoa na fila.
- `StatusAtendimento`: enum com os estados `AGUARDANDO`, `CHAMADO` e `ATENDIDO`.
- Encapsulamento: atributos privados e acesso por getters/setters.
- `PessoaFilaRepository`: camada de acesso ao banco de dados.
- `PessoaFilaService`: camada de regras de negocio.
- `PessoaFilaController`: camada REST que expõe os endpoints da aplicacao.
- DTO `PessoaFilaRequest`: objeto usado para receber dados do cadastro.

## Como rodar

Na raiz do projeto, execute:

```bash
mvn spring-boot:run
```

Depois acesse:

```text
http://localhost:8080
```

Console do H2:

```text
http://localhost:8080/h2-console
```

Dados de conexao do H2:

```text
JDBC URL: jdbc:h2:mem:filasolidaria
User Name: sa
Password: deixe vazio
```

## Como abrir no IntelliJ

1. Abra o IntelliJ IDEA.
2. Escolha a opcao para abrir um projeto existente.
3. Selecione a pasta `ProjetoCompleto`.
4. Aguarde o IntelliJ importar o Maven.
5. Execute a classe `FilaSolidariaApplication`.

## Regras de negocio

- A fila respeita a ordem de chegada.
- Toda pessoa cadastrada inicia com status `AGUARDANDO`.
- Ao chamar a proxima pessoa, o primeiro registro com status `AGUARDANDO` vira `CHAMADO`.
- Uma pessoa chamada pode ser marcada como `ATENDIDO`.
- Nome vazio nao e permitido.
- Telefone vazio nao e permitido.

## Endpoints da API

### Cadastrar pessoa na fila

```http
POST /api/fila
Content-Type: application/json
```

```json
{
  "nome": "Ana Silva",
  "telefone": "(98) 99999-0000",
  "tipoAtendimento": "Clinica"
}
```

### Listar todas as pessoas da fila

```http
GET /api/fila
```

### Listar apenas pessoas aguardando

```http
GET /api/fila/aguardando
```

### Chamar proxima pessoa

```http
PATCH /api/fila/chamar-proximo
```

### Marcar pessoa como atendida

```http
PATCH /api/fila/{id}/atendido
```

Exemplo:

```http
PATCH /api/fila/1/atendido
```

### Remover pessoa da fila

```http
DELETE /api/fila/{id}
```

Exemplo:

```http
DELETE /api/fila/1
```

## Estrutura principal

```text
src/main/java/br/com/filasolidaria
├── controller
├── dto
├── model
├── repository
└── service

src/main/resources/static
├── index.html
├── style.css
└── script.js
```
