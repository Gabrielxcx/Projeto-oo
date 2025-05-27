package view;

import controller.AlunoController; 
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuAluno {

    public static void exibirMenuAluno(Scanner entradaUsuario) { 
        AlunoController alunoController = new AlunoController(); 
        int opcaoAluno = -1;

        System.out.println("\n--- MODO ALUNO ---");

        while (opcaoAluno != 0) {
            System.out.println("\n===== Menu Aluno =====");
            System.out.println("1. Cadastrar Novo Aluno");
            System.out.println("2. Listar Todos os Alunos");
            System.out.println("3. Editar Informações de Aluno");
            System.out.println("4. Trancar Semestre de Aluno");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Sua escolha no Modo Aluno: ");

            try {
                opcaoAluno = entradaUsuario.nextInt();
                entradaUsuario.nextLine(); 

                switch (opcaoAluno) {
                    case 1:
                        System.out.println("\n--- CADASTRO DE ALUNO ---");
                        System.out.print("Nome do Aluno: ");
                        String nome = entradaUsuario.nextLine();
                        System.out.print("Matrícula: ");
                        String matricula = entradaUsuario.nextLine();
                        System.out.print("Curso: ");
                        String curso = entradaUsuario.nextLine();
                        System.out.print("É aluno especial? (sim/nao): ");
                        boolean especial = entradaUsuario.nextLine().equalsIgnoreCase("sim");

                        if (alunoController.cadastrarAluno(nome, matricula, curso, especial)) {
                            System.out.println("Aluno '" + nome + "' cadastrado com sucesso!");
                        } else {
                            System.out.println("Erro: Já existe um aluno com a matrícula '" + matricula + "'.");
                        }
                        break;
                    case 2:
                        System.out.println("\n--- LISTA DE ALUNOS CADASTRADOS ---");
                        if (alunoController.listarAlunos().isEmpty()) {
                            System.out.println("Nenhum aluno cadastrado ainda.");
                        } else {
                            for (model.Aluno aluno : alunoController.listarAlunos()) {
                                System.out.println(aluno.toString()); 
                            }
                        }
                        break;
                    case 3:
                        System.out.println("\n--- EDITAR ALUNO ---");
                        System.out.print("Digite a matrícula do aluno que deseja editar: ");
                        String matEditar = entradaUsuario.nextLine();
                        System.out.print("Novo nome (deixe em branco para não alterar): ");
                        String novoNome = entradaUsuario.nextLine();
                        System.out.print("Novo curso (deixe em branco para não alterar): ");
                        String novoCurso = entradaUsuario.nextLine();

                        
                        model.Aluno alunoExistente = alunoController.buscarAlunoPorMatricula(matEditar);
                        if (alunoExistente == null) {
                            System.out.println("Aluno com matrícula '" + matEditar + "' não encontrado.");
                        } else {
                            
                            String nomeParaSalvar = novoNome.isEmpty() ? alunoExistente.getNome() : novoNome;
                            String cursoParaSalvar = novoCurso.isEmpty() ? alunoExistente.getCurso() : novoCurso;

                            if (alunoController.editarAluno(matEditar, nomeParaSalvar, cursoParaSalvar)) {
                                System.out.println("Aluno com matrícula '" + matEditar + "' atualizado com sucesso!");
                            } else {
                                System.out.println("Não foi possível editar o aluno."); 
                            }
                        }
                        break;
                    case 4:
                        System.out.println("\n--- TRANCAR SEMESTRE ---");
                        System.out.print("Digite a matrícula do aluno para trancar o semestre: ");
                        String matTrancar = entradaUsuario.nextLine();
                        if (alunoController.trancarAluno(matTrancar)) {
                            System.out.println("Semestre do aluno com matrícula '" + matTrancar + "' trancado com sucesso!");
                        } else {
                            System.out.println("Não foi possível trancar o semestre. Aluno não encontrado ou já trancado.");
                        }
                        break;
                    case 0:
                        System.out.println("Voltando ao Menu Principal...");
                        break;
                    default:
                        System.out.println("Opção inválida no Modo Aluno. Por favor, tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nEntrada inválida! Digite apenas números para escolher uma opção.");
                entradaUsuario.nextLine(); 
                opcaoAluno = -1; 
            }
        }
    }
}