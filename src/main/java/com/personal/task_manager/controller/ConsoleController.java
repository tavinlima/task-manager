package com.personal.task_manager.controller;

import com.personal.task_manager.exceptions.NotFoundException;
import com.personal.task_manager.service.CategoriaService;
import com.personal.task_manager.service.ProjetoService;
import com.personal.task_manager.service.TarefaService;
import com.personal.task_manager.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Component
public class ConsoleController implements CommandLineRunner {
    private final TarefaService tarefaService;
    private final UsuarioService usuarioService;
    private final CategoriaService categoriaService;
    private final ProjetoService projetoService;
    Scanner scanner = new Scanner(System.in);
    private int opcao = 0;

    public ConsoleController(TarefaService tarefaService, UsuarioService usuarioService, CategoriaService categoriaService, ProjetoService projetoService) {
        this.usuarioService = usuarioService;
        this.categoriaService = categoriaService;
        this.tarefaService = tarefaService;
        this.projetoService = projetoService;
    }

    public void run(String... args) throws Exception {
        do {
            System.out.println("\n=== Bem vindo ao Task Manager ===");
            System.out.println("\n=== O que deseja fazer? ===");

            System.out.println("1. Gerenciar usuários");
            System.out.println("2. Gerenciar categorias");
            System.out.println("3. Gerenciar tarefas");
            System.out.println("4. Gerenciar projetos");

            System.out.println("0. Sair");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    GerenciarUsuarios();
                    break;
                case 2:
                    GerenciarCategorias();
                    break;
                case 3:
                    GerenciarTarefas();
                    break;
                case 4:
                    GerenciarProjetos();
                    break;

                case 0:
                    System.out.println("Tchau, tchau :)");
                    break;
                default:
                    System.out.println("Selecione uma opção válida");
                    break;
            }
        } while (opcao != 0);

        scanner.close();

    }

    public void GerenciarUsuarios() {
        opcao = 0;

        do {
            System.out.println("\n=== USUÁRIOS ===");

            System.out.println("1. Adicionar usuário");

            System.out.println("2. Listar todos os usuários");
            System.out.println("3. Buscar usuário por ID");
            System.out.println("4. Atualizar um usuário");
            System.out.println("5. Deletar um usuário");

            System.out.println("\n=================");
            System.out.println("6. Voltar");

            opcao = scanner.nextInt();
            scanner.nextLine();


            switch (opcao) {
                case 1:
                    System.out.println("Nome do usuário");
                    String nome = scanner.nextLine();

                    System.out.println("E-mail do usuário");
                    String email = scanner.nextLine();

                    usuarioService.Add(nome, email);
                    System.out.println("Usuário cadastrado com sucesso!");
                    break;
                case 2:
                    System.out.println("Usuários do sistema: ");
                    usuarioService.ListAll().forEach(user -> System.out.println(user.toString()));
                    break;
                case 3: {
                    System.out.println("Digite o id do usuário que deseja procurar: ");
                    long id = Long.parseLong(scanner.nextLine());

                    try {
                        System.out.println(usuarioService.GetById(id));
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 4:
                    boolean usuarioEncontrado;
                    do {
                        System.out.println("Digite o id do usuário que deseja alterar: ");
                        long id = Long.parseLong(scanner.nextLine());

                        try {
                            usuarioService.GetById(id);

                            System.out.println("Digite o novo nome: ");

                            String novoNome = scanner.nextLine();

                            System.out.println("Digite o novo email: ");
                            String novoEmail = scanner.nextLine();

                            usuarioService.Update(id, novoNome, novoEmail);
                            usuarioEncontrado = true;
                        } catch (NotFoundException e) {
                            usuarioEncontrado = false;
                            System.out.println(e.getMessage());
                        }

                    } while (!usuarioEncontrado);

                    break;
                case 5: {
                    System.out.println("Digite o id do usuário que deseja deletar: ");
                    long id = Long.parseLong(scanner.nextLine());

                    try {
                        if(!tarefaService.SearchAssociations(id)){
                            usuarioService.Delete(id);
                            System.out.println("Usuário removido com sucesso!");

                        }
                        System.out.println("Só é permitido excluir um usuário se não houver tarefas associadas a ele!");
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                default:
                    System.out.println("Escolha uma opção válida");
                    break;
            }

        } while (opcao != 6);
    }

    public void GerenciarCategorias() {
        opcao = 0;

        do {
            System.out.println("\n=== CATEGORIAS ===");

            System.out.println("1. Adicionar categoria");

            System.out.println("2. Listar todas as categoria");
            System.out.println("3. Buscar categoria por ID");
            System.out.println("4. Atualizar uma categoria");
            System.out.println("5. Deletar uma categoria");

            System.out.println("\n=================");
            System.out.println("6. Voltar");

            opcao = scanner.nextInt();
            scanner.nextLine();


            switch (opcao) {
                case 1:
                    System.out.println("Título da categoria");
                    String titulo = scanner.nextLine();

                    categoriaService.Add(titulo);
                    System.out.println("Categoria cadastrada com sucesso!");
                    break;
                case 2:
                    System.out.println("Categorias do sistema: ");
                    categoriaService.ListAll().forEach(cat -> System.out.println(cat.toString()));
                    break;
                case 3: {
                    System.out.println("Digite o id da categoria que deseja procurar: ");
                    long id = Long.parseLong(scanner.nextLine());

                    try {
                        System.out.println(categoriaService.GetById(id));
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 4:
                    boolean categoriaEncontrada;
                    do {
                        System.out.println("Digite o id da categoria que deseja alterar: ");
                        long id = Long.parseLong(scanner.nextLine());

                        try {
                            categoriaService.GetById(id);

                            System.out.println("Digite o novo nome: ");
                            String novoNome = scanner.nextLine();

                            categoriaService.Update(id, novoNome);
                            categoriaEncontrada = true;
                        } catch (NotFoundException e) {
                            categoriaEncontrada = false;
                            System.out.println(e.getMessage());
                        }

                    } while (!categoriaEncontrada);
                    break;
                case 5: {
                    System.out.println("Digite o id da categoria que deseja deletar: ");
                    long id = Long.parseLong(scanner.nextLine());

                    try {
                        if(!tarefaService.SearchAssociations(id)){
                            categoriaService.Delete(id);
                            System.out.println("Categoria removida com sucesso!");

                        }
                        System.out.println("Só é permitido excluir uma categoria se não houver tarefas associadas a ela!");
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                default:
                    System.out.println("Escolha uma opção válida");
                    break;
            }

        } while (opcao != 6);
    }

    public void GerenciarTarefas() {
        do {
            System.out.println("\n=== TAREFAS ===");

            System.out.println("1. Adicionar tarefa");

            System.out.println("\n=== Listagens ===");
            System.out.println("2. Listar todas as tarefas");
            System.out.println("3. Listar tarefas vencidas");
            System.out.println("4. Listar tarefas a fazer");
            System.out.println("5. Listar tarefas sendo feitas");
            System.out.println("6. Listar concluídas");

            System.out.println("\n=================");
            System.out.println("7. Deletar uma tarefa");

            System.out.println("0. Voltar");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    System.out.print("Título: ");
                    String titulo = scanner.nextLine();

                    System.out.print("Descrição: ");
                    String descricao = scanner.nextLine();

                    System.out.print("Id do responsável: ");
                    Long idResponsavel = Long.parseLong(scanner.nextLine());

                    System.out.print("Id da categoria: ");
                    Long idCategoria = Long.parseLong(scanner.nextLine());

                    System.out.print("Id do projeto: ");
                    Long idProjeto = Long.parseLong(scanner.nextLine());

                    DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    System.out.print("Data de criação da tarefa: ");
                    LocalDate criacao = LocalDate.parse(scanner.nextLine(), formatador);

                    System.out.print("Data de entrega da tarefa: ");
                    LocalDate prazo = LocalDate.parse(scanner.nextLine(), formatador);

                    tarefaService.Add(titulo, descricao, idResponsavel, idCategoria, idProjeto, criacao, prazo);
                    System.out.println("Tarefa Criada com sucesso!");
                    break;
                case 2:
                    System.out.println("Tarefas criadas: ");
                    tarefaService.ListAll().forEach(tarefa -> System.out.println(tarefa));
                    break;
                case 3:
                    System.out.println("Tarefas vencidas: ");
                    tarefaService.ListAll().forEach(tarefa -> System.out.println(tarefa));
                    break;
                case 4:
                    System.out.println("Tarefas a fazer: ");
                    try {
                        tarefaService.ListByStatus("A_FAZER").forEach(tarefa -> System.out.println(tarefa));
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Tarefas sendo feitas: ");

                    try {
                        tarefaService.ListByStatus("FAZENDO").forEach(tarefa -> System.out.println(tarefa));
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case 6:
                    System.out.println("Tarefas concluídas: ");
                    try {
                        tarefaService.ListByStatus("FEITO").forEach(tarefa -> System.out.println(tarefa));
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case 7:
                    System.out.println("Qual tarefa deseja deletar?");
                    int opcao2 = scanner.nextInt();
                    scanner.nextLine();

                    tarefaService.Delete(opcao2);
                    break;
                default:
            }

        } while (opcao != 0);
    }

    public void GerenciarProjetos() {
        opcao = 0;

        do {
            System.out.println("\n=== PROJETOS ===");

            System.out.println("1. Adicionar projeto");

            System.out.println("2. Listar todos os projetos");
            System.out.println("3. Buscar projeto por ID");
            System.out.println("4. Atualizar um projeto");
            System.out.println("5. Deletar um projeto");

            System.out.println("\n=================");
            System.out.println("6. Voltar");

            opcao = scanner.nextInt();
            scanner.nextLine();


            switch (opcao) {
                case 1:
                    System.out.println("Título do projeto: ");
                    String nome = scanner.nextLine();

                    System.out.println("Descrição do projeto: ");
                    String descricao = scanner.nextLine();

                    projetoService.Add(nome, descricao);
                    System.out.println("Projeto cadastrado com sucesso!");
                    break;
                case 2:
                    System.out.println("Projetos do sistema: ");
                    projetoService.ListAll().forEach(p -> System.out.println(p.toString()));
                    break;
                case 3: {
                    System.out.println("Digite o id do projeto que deseja procurar: ");
                    long id = Long.parseLong(scanner.nextLine());

                    try {
                        System.out.println(projetoService.GetById(id));
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 4:
                    boolean projetoEncontrado;
                    do {
                        System.out.println("Digite o id do usuário que deseja alterar: ");
                        long id = Long.parseLong(scanner.nextLine());

                        try {
                            projetoService.GetById(id);

                            System.out.println("Digite o novo nome: ");
                            String novoNome = scanner.nextLine();

                            System.out.println("Digite a nova descrição: ");
                            String novaDescricao = scanner.nextLine();

                            projetoService.Update(id, novoNome, novaDescricao);
                            projetoEncontrado = true;
                        } catch (NotFoundException e) {
                            projetoEncontrado = false;
                            System.out.println(e.getMessage());
                        }

                    } while (!projetoEncontrado);
                    break;
                case 5: {
                    System.out.println("Digite o id do projeto que deseja deletar: ");
                    long id = Long.parseLong(scanner.nextLine());

                    try {
                        if(!tarefaService.SearchAssociations(id)){
                            projetoService.Delete(id);
                            System.out.println("Projeto removido com sucesso!");
                        }

                        System.out.println("Só é permitido excluir um projetp se não houver tarefas associadas a ele!");
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                default:
                    System.out.println("Escolha uma opção válida");
                    break;
            }

        } while (opcao != 6);
    }
}
