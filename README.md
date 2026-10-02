# usuarios-api

API REST simples para cadastro de usuários (CRUD), feita com Spring Boot e banco H2 em memória.

## Tecnologias

- Java 21
- Spring Boot 4.1 (Web MVC, Data JPA, Validation)
- H2 Database (em memória)
- Maven (via wrapper `mvnw`)

## Como rodar

Pré-requisito: JDK 21 instalado.

```bash
# Linux / macOS / Git Bash
./mvnw spring-boot:run

# Windows (PowerShell / CMD)
mvnw.cmd spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

## Endpoints

| Método | Rota             | Descrição                  | Respostas          |
|--------|------------------|----------------------------|--------------------|
| GET    | `/usuarios`      | Lista todos os usuários    | 200                |
| GET    | `/usuarios/{id}` | Busca um usuário pelo ID   | 200, 404           |
| POST   | `/usuarios`      | Cria um novo usuário       | 200, 400           |
| PUT    | `/usuarios/{id}` | Atualiza um usuário        | 200, 400, 404      |
| DELETE | `/usuarios/{id}` | Remove um usuário          | 204, 404           |

### Modelo

```json
{
  "id": 1,
  "nome": "Gabriel",
  "email": "gabriel@exemplo.com"
}
```

Validações:
- `nome` — obrigatório
- `email` — obrigatório, formato de e-mail válido e único

### Exemplos com curl

```bash
# Criar
curl -X POST http://localhost:8080/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome": "Gabriel", "email": "gabriel@exemplo.com"}'

# Listar
curl http://localhost:8080/usuarios

# Buscar por ID
curl http://localhost:8080/usuarios/1

# Atualizar
curl -X PUT http://localhost:8080/usuarios/1 \
  -H "Content-Type: application/json" \
  -d '{"nome": "Gabriel M. A.", "email": "gabriel@exemplo.com"}'

# Deletar
curl -X DELETE http://localhost:8080/usuarios/1
```

## Console do H2

Com a aplicação rodando, acesse `http://localhost:8080/h2-console` e use:

- **JDBC URL:** `jdbc:h2:mem:usuariosdb`
- **Usuário:** `sa`
- **Senha:** *(em branco)*

> O banco é em memória (`create-drop`): os dados são apagados sempre que a aplicação é reiniciada.

## Testes

```bash
./mvnw test
```
