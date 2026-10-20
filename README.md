# usuarios-api

Projeto que fiz pra estudar Spring Boot: uma API de cadastro de usuários (o famoso CRUD) com MySQL.

Comecei usando o H2, mas os dados sumiam toda vez que eu reiniciava a aplicação, então migrei pro MySQL rodando no Docker. Também coloquei um menu no terminal pra cadastrar sem precisar ficar mandando requisição na mão.

## O que usei

- Java 21
- Spring Boot 4.1 (Web, Data JPA e Validation)
- MySQL 8 no Docker
- H2 só pros testes
- Maven

## Como rodar

Você vai precisar do JDK 21 e do Docker.

**1. Subir o MySQL**

Na primeira vez, cria o container:

```bash
docker run -d --name mysql-usuarios -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=usuariosdb -p 3306:3306 mysql:8.4
```

Das próximas vezes é só ligar:

```bash
docker start mysql-usuarios
```

**2. Rodar a aplicação**

Pela IDE (dando play na `UsuariosApiApplication`) ou pelo terminal:

```bash
./mvnw spring-boot:run
```

Ela sobe em `http://localhost:8080`. Por padrão conecta com usuário `root` e senha `root`. Se o seu MySQL for diferente, dá pra trocar pelas variáveis `DB_USERNAME` e `DB_PASSWORD`.

## Menu no terminal

Quando a aplicação sobe, aparece esse menu no console:

```
===== CADASTRO DE USUÁRIOS =====
1 - Cadastrar usuário
2 - Listar usuários
3 - Excluir usuário
0 - Sair
```

É só digitar o número e dar Enter.

## Endpoints

Se preferir usar a API direto (pelo Postman, Insomnia, curl...):

| Método | Rota             | O que faz              |
|--------|------------------|------------------------|
| GET    | `/usuarios`      | Lista todo mundo       |
| GET    | `/usuarios/{id}` | Busca um usuário       |
| POST   | `/usuarios`      | Cadastra               |
| PUT    | `/usuarios/{id}` | Atualiza               |
| DELETE | `/usuarios/{id}` | Apaga                  |

Exemplo de cadastro:

```bash
curl -X POST http://localhost:8080/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome": "Gabriel", "email": "gabriel@exemplo.com"}'
```

O nome é obrigatório e o email precisa ser válido e não pode estar repetido.

## Testes

Os testes rodam com H2 em memória, então não precisa do MySQL ligado:

```bash
./mvnw test
```
