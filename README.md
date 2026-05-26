# FilaSolidaria

FilaSolidaria é um sistema fullstack simples para gerenciamento de fila virtual de atendimento.

O projeto permite cadastrar pessoas em uma fila, chamar a próxima pessoa por ordem de chegada, finalizar atendimentos e remover cadastros. Ele foi pensado para cenários como clínicas, barbearias, assistências técnicas, secretarias e outros serviços com atendimento por fila.

## Funcionalidades

- Cadastro de pessoas na fila com nome, telefone e tipo de atendimento.
- Listagem de todas as pessoas cadastradas.
- Controle de status do atendimento:
  - `AGUARDANDO`
  - `CHAMADO`
  - `ATENDIDO`
- Chamada automática da próxima pessoa aguardando.
- Marcação de pessoa chamada como atendida.
- Remoção de pessoas da fila.
- Interface web simples consumindo a API REST.
- Banco H2 em memória para facilitar execução local.

## Tecnologias Utilizadas

### Back-end

- Java 17
- Spring Boot 3.5.0
- Spring Web
- Spring Data JPA
- Bean Validation
- H2 Database
- Maven

### Front-end

- HTML
- CSS
- JavaScript puro
- Fetch API para comunicação com o back-end

## Estrutura do Projeto

```text
ProjetoCompleto
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java
    │   │   └── br/com/filasolidaria
    │   │       ├── controller
    │   │       ├── dto
    │   │       ├── model
    │   │       ├── repository
    │   │       ├── service
    │   │       └── FilaSolidariaApplication.java
    │   └── resources
    │       ├── application.properties
    │       └── static
    │           ├── index.html
    │           ├── script.js
    │           └── style.css
    └── test
```

## Pré-requisitos

Antes de rodar o projeto, instale:

- Java JDK 17 ou superior
- Maven 3.8 ou superior
- Git, caso queira clonar o repositório

Para verificar se Java e Maven estão instalados:

```bash
java -version
mvn -version
```

## Como Baixar o Projeto

Clone o repositório:

```bash
git clone https://github.com/rafaeldev-05/Fullstack-java-springboot.git
```

Entre na pasta do projeto:

```bash
cd Fullstack-java-springboot
```

## Como Rodar

Na raiz do projeto, execute:

```bash
mvn spring-boot:run
```

Quando a aplicação iniciar, acesse no navegador:

```text
http://localhost:8080
```

## Como Usar o Sistema

### 1. Cadastrar uma Pessoa na Fila

Na tela inicial, preencha:

- Nome
- Telefone
- Tipo de atendimento

Depois clique em `Cadastrar`.

A pessoa será adicionada à fila com o status `AGUARDANDO`.

### 2. Chamar a Próxima Pessoa

Clique no botão `Chamar próximo`.

O sistema busca a primeira pessoa com status `AGUARDANDO`, respeitando a ordem de chegada, e altera o status para `CHAMADO`.

### 3. Finalizar um Atendimento

Quando uma pessoa estiver com status `CHAMADO`, aparecerá o botão `Atendido` na linha dela.

Clique em `Atendido` para alterar o status para `ATENDIDO`.

### 4. Remover uma Pessoa da Fila

Clique em `Remover` na linha da pessoa desejada.

O cadastro será excluído da fila.

### 5. Atualizar a Lista

Clique em `Atualizar` para recarregar a listagem da fila.

## Regras de Negócio

- Toda pessoa cadastrada entra automaticamente com status `AGUARDANDO`.
- A fila é ordenada pela data e hora de entrada.
- Apenas pessoas com status `AGUARDANDO` podem ser chamadas.
- Apenas pessoas com status `CHAMADO` podem ser marcadas como `ATENDIDO`.
- Nome, telefone e tipo de atendimento são obrigatórios.
- O banco H2 é em memória, então os dados são apagados quando a aplicação é encerrada.

## Endpoints da API

Base URL:

```text
http://localhost:8080/api/fila
```

### Cadastrar Pessoa

```http
POST /api/fila
Content-Type: application/json
```

Exemplo de corpo:

```json
{
  "nome": "Ana Silva",
  "telefone": "(98) 99999-0000",
  "tipoAtendimento": "Clínica"
}
```

Exemplo usando PowerShell:

```powershell
Invoke-RestMethod `
  -Uri "http://localhost:8080/api/fila" `
  -Method Post `
  -ContentType "application/json" `
  -Body '{"nome":"Ana Silva","telefone":"(98) 99999-0000","tipoAtendimento":"Clínica"}'
```

### Listar Todas as Pessoas

```http
GET /api/fila
```

PowerShell:

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/fila" -Method Get
```

### Listar Pessoas Aguardando

```http
GET /api/fila/aguardando
```

PowerShell:

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/fila/aguardando" -Method Get
```

### Chamar Próxima Pessoa

```http
PATCH /api/fila/chamar-proximo
```

PowerShell:

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/fila/chamar-proximo" -Method Patch
```

### Marcar Pessoa como Atendida

```http
PATCH /api/fila/{id}/atendido
```

Exemplo:

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/fila/1/atendido" -Method Patch
```

### Remover Pessoa

```http
DELETE /api/fila/{id}
```

Exemplo:

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/fila/1" -Method Delete
```

## Banco de Dados H2

O projeto usa H2 em memória. Para acessar o console web:

```text
http://localhost:8080/h2-console
```

Dados de conexão:

```text
JDBC URL: jdbc:h2:mem:filasolidaria
User Name: sa
Password: deixe vazio
```

## Principais Classes

- `FilaSolidariaApplication`: classe principal da aplicação Spring Boot.
- `PessoaFila`: entidade que representa uma pessoa cadastrada na fila.
- `StatusAtendimento`: enum com os status da fila.
- `PessoaFilaRequest`: DTO usado para receber dados de cadastro.
- `PessoaFilaRepository`: camada de acesso aos dados.
- `PessoaFilaService`: camada com as regras de negócio.
- `PessoaFilaController`: controller REST da API.
- `ApiExceptionHandler`: tratamento centralizado de erros da API.

## Como Rodar os Testes

Execute:

```bash
mvn test
```

## Como Abrir no IntelliJ IDEA

1. Abra o IntelliJ IDEA.
2. Clique em `Open`.
3. Selecione a pasta do projeto.
4. Aguarde a importação do Maven.
5. Execute a classe `FilaSolidariaApplication`.
6. Acesse `http://localhost:8080`.

## Autor

Projeto desenvolvido para estudo de Java, Spring Boot, API REST, orientação a objetos e integração com front-end simples.
