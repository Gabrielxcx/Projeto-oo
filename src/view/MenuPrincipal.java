package view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuPrincipal {

    public static void exibirMenu() {
        Scanner entradaUsuario = new Scanner(System.in);
        int opcaoEscolhida = -1;

        System.out.println("Bem-vindo ao Sistema de Gerenciamento Acadêmico!");
        System.out.println("----------------------------------------------");

        while (opcaoEscolhida != 0) {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Acessar o Modo Aluno");
            System.out.println("2. Gerenciar Disciplinas e Turmas");
            System.out.println("3. Lançar Avaliações e Frequências");
            System.out.println("0. Sair do Sistema");
            System.out.print("Sua escolha: ");

            try {
                opcaoEscolhida = entradaUsuario.nextInt();
                entradaUsuario.nextLine(); 

                switch (opcaoEscolhida) {
                    case 1:
                        System.out.println("\nEntrando no Modo Aluno...");
                        MenuAluno.exibirMenuAluno(entradaUsuario);
                        break;
                    case 2:
                        System.out.println("\nEntrando no Modo Disciplina/Turma...");
                        MenuDisciplinaTurma.exibirMenuDisciplinaTurma(entradaUsuario);
                        break;
                    case 3:
                        System.out.println("\nEntrando no Modo Avaliação/Frequência...");
                        MenuAvaliacaoFrequencia.exibirMenuAvaliacaoFrequencia(entradaUsuario);
                        break;
                    case 0:
                        System.out.println("\nObrigado por usar nosso sistema. Até a próxima!");
                        break;
                    default:
                        System.out.println("\nOpção inválida. Por favor, escolha um número entre 0 e 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nEntrada inválida! Por favor, digite apenas números para escolher uma opção.");
                entradaUsuario.nextLine();
                opcaoEscolhida = -1;
            }
        }
        entradaUsuario.close();
    }
}