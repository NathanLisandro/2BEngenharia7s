# Sistema de Gerenciamento de Tarefas (API REST)

Este projeto é uma API REST para gerenciamento de tarefas, similar a um Trello simplificado, construída com Java 21 e Spring Boot.

## Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- H2 Database (em memória)
- Spring Security
- JWT (JSON Web Token)
- Lombok

## Estrutura do Projeto

O projeto segue uma estrutura de camadas MVC (Model-View-Controller) adaptada para APIs REST:

- `model`: Contém as entidades de domínio (e.g., `Tarefa`, `Usuario`).
- `repository`: Interfaces para acesso a dados, estendendo `JpaRepository`.
- `service`: Implementa a lógica de negócio.
- `controller`: Expõe os endpoints REST.
- `config`: Configurações de segurança (Spring Security).
- `filter`: Implementação do filtro JWT.
- `util`: Utilitários (e.g., `JwtUtil` para JWT).
- `dto`: Objetos de Transferência de Dados para requisições de autenticação.

## Como Executar a Aplicação

1.  **Pré-requisitos:**
    *   Java Development Kit (JDK) 21
    *   Maven

2.  **Clonar o repositório (se aplicável):**
    ```bash
    git clone <URL_DO_SEU_REPOSITORIO>
    cd gerenciador-de-tarefas
    ```

3.  **Compilar e Executar:**
    Você pode compilar e executar o projeto usando Maven:
    ```bash
    ./mvnw spring-boot:run
    ```
    Ou, se preferir, pode gerar o pacote JAR e executá-lo:
    ```bash
    ./mvnw clean install
    java -jar target/main-0.0.1-SNAPSHOT.jar
    ```

    A aplicação será iniciada na porta padrão 8080.

## Endpoints da API

A API possui endpoints para gerenciamento de tarefas e autenticação de usuários.

### Autenticação

Todos os endpoints de tarefas (`/tarefas`) exigem autenticação via JWT.

**1. Registrar um novo usuário**

-   **URL:** `/auth/register`
-   **Método:** `POST`
-   **Corpo da Requisição (JSON):**
    ```json
    {
        "username": "seu_usuario",
        "password": "sua_senha"
    }
    ```
-   **Resposta (Sucesso - 201 Created):**
    ```
    Usuário registrado com sucesso!
    ```
-   **Resposta (Erro - 400 Bad Request):**
    ```
    Usuário já existe!
    ```

**2. Fazer login e obter um token JWT**

-   **URL:** `/auth/login`
-   **Método:** `POST`
-   **Corpo da Requisição (JSON):**
    ```json
    {
        "username": "seu_usuario",
        "password": "sua_senha"
    }
    ```
-   **Resposta (Sucesso - 200 OK):** Retorna o token JWT como uma string simples no corpo da resposta.
    ```
    eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzZXVfdXN1YXJpbyIsImlhdCI6MTY3ODg4NjQwMCwiZXhwIjoxNjc4ODg5MjAwfQ....
    ```
-   **Resposta (Erro - 401 Unauthorized):**
    ```
    Credenciais inválidas!
    ```

### Gerenciamento de Tarefas

Todos os endpoints abaixo requerem um token JWT válido no cabeçalho `Authorization` no formato `Bearer <YOUR_JWT_TOKEN>`.

**Exemplo de Cabeçalho:**

`Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzZXVfdXN1YXJpbyIsImlhdCI6MTY3ODg4NjQwMCwiZXhwIjoxNjc4ODg5MjAwfQ....`

**1. Cadastrar uma nova tarefa**

-   **URL:** `/tarefas`
-   **Método:** `POST`
-   **Corpo da Requisição (JSON):**
    ```json
    {
        "titulo": "Comprar mantimentos",
        "descricao": "Leite, pão, ovos, frutas",
        "status": "PENDENTE"
    }
    ```
-   **Resposta (Sucesso - 201 Created):** Retorna a tarefa cadastrada com o ID.
    ```json
    {
        "id": 1,
        "titulo": "Comprar mantimentos",
        "descricao": "Leite, pão, ovos, frutas",
        "status": "PENDENTE"
    }
    ```

**2. Listar todas as tarefas**

-   **URL:** `/tarefas`
-   **Método:** `GET`
-   **Resposta (Sucesso - 200 OK):** Retorna uma lista de tarefas.
    ```json
    [
        {
            "id": 1,
            "titulo": "Comprar mantimentos",
            "descricao": "Leite, pão, ovos, frutas",
            "status": "PENDENTE"
        },
        {
            "id": 2,
            "titulo": "Estudar Spring Security",
            "descricao": "Revisar JWT e OAuth2",
            "status": "EM_ANDAMENTO"
        }
    ]
    ```

**3. Atualizar o status de uma tarefa**

-   **URL:** `/tarefas/{id}` (substitua `{id}` pelo ID da tarefa)
-   **Método:** `PATCH`
-   **Corpo da Requisição (String simples - não JSON):** O novo status da tarefa.
    ```
    CONCLUIDA
    ```
-   **Resposta (Sucesso - 200 OK):** Retorna a tarefa atualizada.
    ```json
    {
        "id": 1,
        "titulo": "Comprar mantimentos",
        "descricao": "Leite, pão, ovos, frutas",
        "status": "CONCLUIDA"
    }
    ```
-   **Resposta (Erro - 404 Not Found):** Se a tarefa não for encontrada.

**4. Remover uma tarefa**

-   **URL:** `/tarefas/{id}` (substitua `{id}` pelo ID da tarefa)
-   **Método:** `DELETE`
-   **Resposta (Sucesso - 204 No Content):** Se a tarefa for removida com sucesso.
-   **Resposta (Erro - 404 Not Found):** Se a tarefa não for encontrada.

## Configuração do Banco de Dados H2

O projeto utiliza o banco de dados H2 em memória. Você pode acessar o console do H2 em:

`http://localhost:8080/h2-console`

Utilize as seguintes credenciais:

-   **Driver Class:** `org.h2.Driver`
-   **JDBC URL:** `jdbc:h2:mem:gerenciadordetarefasdb`
-   **User Name:** `sa`
-   **Password:** (deixe em branco)

## Propriedade JWT Secret

A chave secreta para a geração e validação do JWT está configurada em `src/main/resources/application.properties` sob a propriedade `jwt.secret`.

É **altamente recomendável** que você altere esta chave para uma string complexa e única em um ambiente de produção. 