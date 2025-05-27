package view;

import controller.AlunoController;
import java.util.List;
import java.util.Scanner;
import model.Aluno;

public class AlunoView {
    public  AlunoController controller = new AlunoController();
    private Scanner sc = new Scanner(System.in);

    public void menuAluno() {
        int opcao;
        do {
            System.out.println("\n--- MODO ALUNO ---");
            System.out.println("1. Cadastrar aluno");
            System.out.println("2. Editar aluno");
            System.out.println("3. Trancar semestre");
            System.out.println("4. Listar alunos");
            System.out.println("0. Voltar");
            opcao = sc.nextInt(); sc.nextLine();

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> editar();
                case 3 -> trancar();
                case 4 -> listar();
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Matrícula: ");
        String mat = sc.nextLine();
        System.out.print("Curso: ");
        String curso = sc.nextLine();
        System.out.print("É aluno especial? (s/n): ");
        boolean esp = sc.nextLine().equalsIgnoreCase("s");
        if (controller.cadastrarAluno(nome, mat, curso, esp))
            System.out.println("Aluno cadastrado com sucesso.");
        else
            System.out.println("Já existe um aluno com essa matrícula.");
    }

    private void editar() {
        System.out.print("Matrícula do aluno: ");
        String mat = sc.nextLine();
        System.out.print("Novo nome: ");
        String nome = sc.nextLine();
        System.out.print("Novo curso: ");
        String curso = sc.nextLine();
        if (controller.editarAluno(mat, nome, curso))
            System.out.println("Aluno atualizado.");
        else
            System.out.println("Aluno não encontrado.");
    }

    private void trancar() {
        System.out.print("Matrícula do aluno: ");
        String mat = sc.nextLine();
        if (controller.trancarAluno(mat))
            System.out.println("Semestre trancado.");
        else
            System.out.println("Aluno não encontrado.");
    }

    private void listar() {
        List<Aluno> lista = controller.listarAlunos();
        for (Aluno a : lista) {
            System.out.printf("Nome: %s | Matrícula: %s | Curso: %s | Especial: %s\n",
                a.getNome(), a.getMatricula(), a.getCurso(), !a.recebeNota());
        }
    }
}
