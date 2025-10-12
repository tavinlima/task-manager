package com.personal.task_manager.controller;

import com.personal.task_manager.domain.Tarefa;
import com.personal.task_manager.enums.Prioridade;
import com.personal.task_manager.enums.Status;
import com.personal.task_manager.exceptions.NotFoundException;
import com.personal.task_manager.service.CategoriaService;
import com.personal.task_manager.service.ProjetoService;
import com.personal.task_manager.service.TarefaService;
import com.personal.task_manager.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

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
            // Menu criado no console para que as funções desse código possam ser acessadas
            System.out.println("\n=== Bem vindo ao Task Manager ===");
            System.out.println("\n=== O que deseja fazer? ===");

            //Cada 'número' chama um menu diferente para que seja possível gerenciar o objeto que o usuário deseja acessar
            System.out.println("1. Gerenciar usuários");
            System.out.println("2. Gerenciar categorias");
            System.out.println("3. Gerenciar tarefas");
            System.out.println("4. Gerenciar projetos");

            System.out.println("0. Sair");
            //Ou simplesmente deseja sair da aplicação
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            //Switch para o código direcione a opção selecionada pelo usuário
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
                    //Mensagem para que o usuário selecione apenas opções válidas
                    System.out.println("Selecione uma opção válida");
                    break;
            }
            //e um do/while para que o menu seja mostrado enquanto ele não desejar sair
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
                    //Mensagem que pergunta os dados do usuário que deseja ser cadastrado, ao final é chamada a função que faz isso
                    System.out.println("Nome do usuário");
                    //Váriavel para armazenar a informação digitada no console
                    String nome = scanner.nextLine();

                    System.out.println("E-mail do usuário");
                    String email = scanner.nextLine();

                    //Chamando a função que realiza o cadastro
                    usuarioService.Add(nome, email);
                    System.out.println("Usuário cadastrado com sucesso!");
                    break;
                case 2:
                    System.out.println("Usuários do sistema: ");

                    usuarioService.ListAll().forEach(user -> {
                        System.out.println("Nome: " + user.getNome());
                        System.out.println("E-mail: " + user.getEmail());
                    });

                    break;
                case 3: {
                    //Mensagem que pergunta o id que será buscado para saber se um usuário existe
                    System.out.println("Digite o id do usuário que deseja procurar: ");
                    long id = Long.parseLong(scanner.nextLine());

                    //Verificação para retornar o usuário caso seja encontrado, ou lançar a exceção caso o usuário ainda não exista
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
                        //Verificação para alterar o usuário caso seja encontrado, ou lançar a exceção caso o usuário ainda não exista
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
                    //Verificação para impedir que um usuário associado a uma tarefa seja excluído
                    try {
                        if (!tarefaService.SearchAssociations(id)) {
                            //Caso não exista nenhuma associação, o usuário é deletado
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
                        if (!tarefaService.SearchAssociations(id)) {
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
            System.out.println("7. Atualizar uma tarefa");
            System.out.println("8. Mudar status de uma tarefa");
            System.out.println("9. Deletar uma tarefa");

            System.out.println("-1. Voltar");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            boolean existe;
            Long idResponsavel = 0L;
            Long idCategoria = 0L;
            Long idProjeto = 0L;
            switch (opcao) {
                case 1:
                    System.out.print("Título: ");
                    String titulo = scanner.nextLine();

                    System.out.print("Descrição: ");
                    String descricao = scanner.nextLine();

                    do {
                        System.out.print("Id do responsável: ");
                        idResponsavel = Long.parseLong(scanner.nextLine());
                        try{
                            usuarioService.GetById(idResponsavel);
                            existe = true;
                        } catch (NotFoundException e) {
                            System.out.println(e.getMessage());
                            existe = false;
                        }
                    } while (!existe);

                    do {
                        System.out.print("Id da categoria: ");
                        idCategoria = Long.parseLong(scanner.nextLine());
                        try{
                            categoriaService.GetById(idCategoria);
                            existe = true;
                        } catch (NotFoundException e) {
                            System.out.println(e.getMessage());
                            existe = false;
                        }
                    } while (!existe);

                    do {
                        System.out.print("Id do projeto: ");
                        idProjeto = Long.parseLong(scanner.nextLine());
                        try{
                            projetoService.GetById(idProjeto);
                            existe = true;
                        } catch (NotFoundException e) {
                            System.out.println(e.getMessage());
                            existe = false;
                        }
                    } while (!existe);

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
                    System.out.println("Quantidade de tarefas: " + (long) tarefaService.ListAll().size());


                    //O Map é um objeto que mapeia valores para as chaves
                    //Nesse caso, a chave seria o nome do usuário que quero descobrir as tarefas
                    //E o valor, a quantidade de tarefas associadas a ele
                    //Essa função .collect agrega os elementos da lista por um argumento, nesse caso, pelo Id do responsável
                    Map<Long, Long> tarefasPorUsuario = tarefaService.ListAll().stream().collect(
                            Collectors.groupingBy(
                                    Tarefa::getIdResponsavel,
                                    Collectors.counting()
                            ));
                    tarefasPorUsuario.forEach((id, quantidade) -> {
                        System.out.println("Usuário: "+  usuarioService.GetById(id).getNome() + ". Quantidade de tarefas: " + quantidade);
                    });

                    Map<Long, Long> tarefasPorProjeto = tarefaService.ListAll().stream().collect(
                            Collectors.groupingBy(
                                    Tarefa::getIdProjeto,
                                    Collectors.counting()
                            ));

                    tarefasPorProjeto.forEach((id, quantidade) -> {
                        System.out.println("Projeto: "+  projetoService.GetById(id).getNome() + ". Quantidade de tarefas: " + quantidade);
                    });

                    tarefaService.ListAll().forEach(this::ListTarefa);

                    break;
                case 3:
                    System.out.println("Tarefas vencidas: ");
                    tarefaService.ListAll().forEach(tarefa -> {
                        if (tarefa.getPrazo().isBefore(LocalDate.now())) ListTarefa(tarefa);
                    });
                    break;
                case 4:
                    System.out.println("Tarefas a fazer: ");
                    try {
                        tarefaService.ListByStatus("A_FAZER").forEach(this::ListTarefa);
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Tarefas sendo feitas: ");

                    try {
                        tarefaService.ListByStatus("FAZENDO").forEach(this::ListTarefa);
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case 6:
                    System.out.println("Tarefas concluídas: ");
                    try {
                        tarefaService.ListByStatus("FEITO").forEach(this::ListTarefa);
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case 7: {
                    formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    System.out.println("Qual o id da tarefa deseja alterar?");
                    Long id = Long.parseLong(scanner.nextLine());

                    System.out.print("Título: ");
                    titulo = scanner.nextLine();

                    System.out.print("Descrição: ");
                    descricao = scanner.nextLine();
                    existe = false;

                    do {
                        System.out.print("Id do responsável: ");
                        idResponsavel = Long.parseLong(scanner.nextLine());
                        if (usuarioService.GetById(idResponsavel) != null) existe = true;
                    } while (!existe);

                    System.out.print("Id da categoria: ");
                    idCategoria = Long.parseLong(scanner.nextLine());

                    System.out.print("Id do projeto: ");
                    idProjeto = Long.parseLong(scanner.nextLine());

                    System.out.print("Data de criação da tarefa: ");
                    criacao = LocalDate.parse(scanner.nextLine(), formatador);

                    System.out.print("Data de entrega da tarefa: ");
                    prazo = LocalDate.parse(scanner.nextLine(), formatador);

                    System.out.print("Prioridade: ");
                    System.out.print("1 - Baixa");
                    System.out.print("2 - Média");
                    System.out.print("3 - Alta");
                    int prioridade = scanner.nextInt();
                    scanner.nextLine();

                    //Como a prioridade é um enum, entendi que a melhor forma de deixar a opção para o usuário é com o switch case das opções
                    //cadastradas no enum
                    Prioridade p = switch (prioridade) {
                        case 1 -> Prioridade.BAIXA;
                        case 2 -> Prioridade.MEDIA;
                        case 3 -> Prioridade.ALTA;
                        default -> Prioridade.BAIXA;
                    };

                    System.out.print("Status: ");
                    System.out.print("1 - A fazer");
                    System.out.print("2 - Fazendo");
                    System.out.print("3 - Feito");
                    int status = scanner.nextInt();
                    scanner.nextLine();

                    //Mesmo caso da prioridade
                    Status s = switch (status) {
                        case 1 -> Status.A_FAZER;
                        case 2 -> Status.FAZENDO;
                        case 3 -> Status.FEITO;
                        default -> Status.A_FAZER;
                    };

                    try {
                        tarefaService.Update(id, titulo, descricao, idResponsavel, idCategoria, idProjeto, criacao, prazo, s, p);
                        System.out.println("Tarefa atualizada com sucesso!");
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                    }

                }
                break;
                case 8:
                    System.out.println("Tarefas concluídas: ");
                    try {
                        tarefaService.ListByStatus("FEITO").forEach(this::ListTarefa);
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case 9:
                    System.out.println("Qual tarefa deseja deletar?");
                    Long id = Long.parseLong(scanner.nextLine());

                    try {
                        tarefaService.Delete(id);
                        System.out.println("Tarefa deletado com sucesso!");
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                default:
            }

        } while (opcao != -1);
    }

    private void ListTarefa(Tarefa tarefa) {
        //Como existem muitas listagens com as mesmas informações, optei por colocar todas em uma funçao só e chamar quando necessário
        //para evitar repetição excessiva de código
        System.out.println("\n========================");
        if (tarefa.getStatus().equals(Status.EXCLUIDA)) {
            //Verificação para informar se a tarefa foi excluída ou não
            System.out.println("\nTarefa excluída: ");
        }
        System.out.println("Título: " + tarefa.getTitulo());
        System.out.println("Descrição: " + tarefa.getDescricao());
        System.out.println("Status: " + tarefa.getStatus());
        System.out.println("Prioridade: " + tarefa.getPrioridade());
        System.out.println("Data de criação: " + tarefa.getCriacao());
        System.out.println("Prazo para conclusão: " + tarefa.getPrazo());
        System.out.println("Responsável: " + usuarioService.GetById(tarefa.getIdResponsavel()).getNome());
        System.out.println("Projeto: " + projetoService.GetById(tarefa.getIdProjeto()).getNome());
        System.out.println("Categoria: " + categoriaService.GetById(tarefa.getIdCategoria()).getNome());
        System.out.println("========================\n");
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
                        if (!tarefaService.SearchAssociations(id)) {
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
