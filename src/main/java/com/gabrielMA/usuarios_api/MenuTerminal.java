package com.gabrielMA.usuarios_api;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

// Menu no terminal que abre junto com a API (desligado nos testes via app.menu-terminal=false)
@Component
@ConditionalOnProperty(name = "app.menu-terminal", havingValue = "true", matchIfMissing = true)
public class MenuTerminal implements CommandLineRunner {

    private final UsuarioRepository repository;
    private final Validator validator;
    private final ConfigurableApplicationContext context;
    // Lê o teclado na mesma codificação do console, para nomes com acento (ex: João) não saírem quebrados
    private final Scanner scanner = new Scanner(System.in,
            Charset.forName(System.getProperty("stdout.encoding", Charset.defaultCharset().name())));

    public MenuTerminal(UsuarioRepository repository, Validator validator, ConfigurableApplicationContext context) {
        this.repository = repository;
        this.validator = validator;
        this.context = context;
    }

    @Override
    public void run(String... args) {
        while (true) {
            System.out.println();
            System.out.println("===== CADASTRO DE USUÁRIOS =====");
            System.out.println("1 - Cadastrar usuário");
            System.out.println("2 - Listar usuários");
            System.out.println("3 - Excluir usuário");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            if (!scanner.hasNextLine()) {
                return;
            }

            switch (scanner.nextLine().trim()) {
                case "1" -> cadastrar();
                case "2" -> listar();
                case "3" -> excluir();
                case "0" -> {
                    System.out.println("Encerrando a aplicação...");
                    context.close();
                    return;
                }
                default -> System.out.println("Opção inválida, tente de novo.");
            }
        }
    }

    private void cadastrar() {
        Usuario usuario = new Usuario();
        System.out.print("Nome: ");
        usuario.setNome(scanner.nextLine().trim());
        System.out.print("Email: ");
        usuario.setEmail(scanner.nextLine().trim());

        // Usa as mesmas regras da entidade (@NotBlank, @Email)
        Set<ConstraintViolation<Usuario>> erros = validator.validate(usuario);
        if (!erros.isEmpty()) {
            erros.forEach(erro -> System.out.println("Erro: " + erro.getMessage()));
            return;
        }

        try {
            Usuario salvo = repository.save(usuario);
            System.out.println("Usuário cadastrado com ID " + salvo.getId() + "!");
        } catch (DataIntegrityViolationException e) {
            System.out.println("Erro: já existe um usuário com esse email.");
        }
    }

    private void listar() {
        List<Usuario> usuarios = repository.findAll();
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado ainda.");
            return;
        }

        System.out.println();
        System.out.printf("%-5s %-30s %s%n", "ID", "NOME", "EMAIL");
        for (Usuario usuario : usuarios) {
            System.out.printf("%-5d %-30s %s%n", usuario.getId(), usuario.getNome(), usuario.getEmail());
        }
    }

    private void excluir() {
        System.out.print("ID do usuário a excluir: ");
        String entrada = scanner.nextLine().trim();

        long id;
        try {
            id = Long.parseLong(entrada);
        } catch (NumberFormatException e) {
            System.out.println("Erro: digite um número.");
            return;
        }

        if (!repository.existsById(id)) {
            System.out.println("Usuário com ID " + id + " não encontrado.");
            return;
        }

        repository.deleteById(id);
        System.out.println("Usuário excluído.");
    }
}
