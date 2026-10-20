# usuarios-api

API REST simples para cadastro de usuários (CRUD), feita com Spring Boot e banco MySQL.

## Tecnologias

- Java 21
- Spring Boot 4.1 (Web MVC, Data JPA, Validation)
- MySQL 8
- H2 Database (apenas nos testes)
- Maven (via wrapper `mvnw`)

## Como rodar

Pré-requisitos:

- JDK 21 instalado
- MySQL rodando em `localhost:3306`

O banco `usuariosdb` é criado automaticamente na primeira execução. Por padrão a aplicação conecta com usuário `root` e senha `root`; para usar outras credenciais, defina as variáveis de ambiente `DB_USERNAME` e `DB_PASSWORD`:

```bash
# Git Bash
DB_USERNAME=meu_usuario DB_PASSWORD=minha_senha ./mvnw spring-boot:run

# PowerShell
$env:DB_USERNAME="meu_usuario"; $env:DB_PASSWORD="minha_senha"; .\mvnw.cmd spring-boot:run
```

Para subir um MySQL rapidamente com Docker:

```bash
docker run -d --name mysql-usuarios -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root mysql:8
```

Rodando com as credenciais padrão:

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

## Banco de dados

As tabelas são criadas/atualizadas automaticamente pelo Hibernate (`ddl-auto=update`), e os dados persistem entre reinicializações da aplicação.

## Testes

Os testes usam H2 em memória (configurado em `src/test/resources/application.properties`), então não precisam de MySQL rodando.

```bash
./mvnw test
```
