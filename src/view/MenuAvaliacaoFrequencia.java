package view;

import controller.AvaliacaoFrequenciaController;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import model.Avaliacao;
import model.Frequencia;

public class MenuAvaliacaoFrequencia {

    public static void exibirMenuAvaliacaoFrequencia(Scanner entradaUsuario) {
        AvaliacaoFrequenciaController controller = new AvaliacaoFrequenciaController();
        int opcaoAF = -1; //Menu de Avaliação/Frequência

        System.out.println("\n--- MODO AVALIAÇÃO E FREQUÊNCIA ---");

        while (opcaoAF != 0) {
            System.out.println("\n===== Menu Avaliação/Frequência =====");
            System.out.println("1. Lançar Nota de Avaliação");
            System.out.println("2. Registrar Frequência em Aula");
            System.out.println("3. Listar Avaliações por Aluno");
            System.out.println("4. Listar Frequências por Aluno");
            System.out.println("5. Calcular Porcentagem de Frequência de Aluno em Turma");
            System.out.println("6. Calcular Média Final de Aluno em Turma"); // NOVA OPÇÃO
            System.out.println("7. Verificar Status de Aprovação do Aluno"); // NOVA OPÇÃO
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Sua escolha no Modo Avaliação/Frequência: ");

            try {
                opcaoAF = entradaUsuario.nextInt();
                entradaUsuario.nextLine(); 

                switch (opcaoAF) {
                    case 1:
                        System.out.println("\n--- LANÇAR NOTA ---");
                        System.out.print("Matrícula do Aluno: ");
                        String matAlunoNota = entradaUsuario.nextLine();
                        System.out.print("Código da Disciplina: ");
                        String codDiscNota = entradaUsuario.nextLine();
                        System.out.print("Semestre da Turma (ex: 2024.1): ");
                        String semTurmaNota = entradaUsuario.nextLine();
                        System.out.print("Tipo de Avaliação (ex: P1, Trabalho): ");
                        String tipoAval = entradaUsuario.nextLine();
                        System.out.print("Nota (use vírgula para decimal, ex: 8,5): ");
                        String notaStr = entradaUsuario.nextLine();
                        double nota = Double.parseDouble(notaStr.replace(",", "."));

                        System.out.print("Data da Avaliação (DD/MM/AAAA): ");
                        String dataAvalStr = entradaUsuario.nextLine();
                        LocalDate dataAval = parseDate(dataAvalStr); 

                        if (dataAval == null) {
                            System.out.println("Erro: Data da avaliação inválida. Tente novamente.");
                            break;
                        }

                        if (controller.lancarNota(matAlunoNota, codDiscNota, semTurmaNota, tipoAval, nota, dataAval)) {
                            System.out.println("Nota lançada com sucesso!");
                        } else {
                            System.out.println("Falha ao lançar nota. Verifique os dados e tente novamente.");
                        }
                        break;

                    case 2:
                        System.out.println("\n--- REGISTRAR FREQUÊNCIA ---");
                        System.out.print("Matrícula do Aluno: ");
                        String matAlunoFreq = entradaUsuario.nextLine();
                        System.out.print("Código da Disciplina: ");
                        String codDiscFreq = entradaUsuario.nextLine();
                        System.out.print("Semestre da Turma (ex: 2024.1): ");
                        String semTurmaFreq = entradaUsuario.nextLine();
                        System.out.print("Data da Aula (DD/MM/AAAA): ");
                        String dataAulaStr = entradaUsuario.nextLine();
                        LocalDate dataAula = parseDate(dataAulaStr); 

                        if (dataAula == null) {
                            System.out.println("Erro: Data da aula inválida. Tente novamente.");
                            break;
                        }

                        System.out.print("Presente? (sim/nao): ");
                        boolean presente = entradaUsuario.nextLine().equalsIgnoreCase("sim");

                        if (controller.registrarFrequencia(matAlunoFreq, codDiscFreq, semTurmaFreq, dataAula, presente)) {
                            System.out.println("Frequência registrada com sucesso!");
                        } else {
                            System.out.println("Falha ao registrar frequência. Aluno já registrado para esta aula ou dados inválidos.");
                        }
                        break;

                    case 3:
                        System.out.println("\n--- LISTAR AVALIAÇÕES POR ALUNO ---");
                        System.out.print("Matrícula do Aluno para listar avaliações: ");
                        String matAlunoListarAval = entradaUsuario.nextLine();
                        List<Avaliacao> avaliacoesAluno = controller.listarAvaliacoesPorAluno(matAlunoListarAval);

                        if (avaliacoesAluno.isEmpty()) {
                            System.out.println("Nenhuma avaliação encontrada para o aluno " + matAlunoListarAval + ".");
                        } else {
                            System.out.println("Avaliações do aluno " + matAlunoListarAval + ":");
                            for (Avaliacao aval : avaliacoesAluno) {
                                System.out.println(aval.toString());
                            }
                        }
                        break;

                    case 4:
                        System.out.println("\n--- LISTAR FREQUÊNCIAS POR ALUNO ---");
                        System.out.print("Matrícula do Aluno para listar frequências: ");
                        String matAlunoListarFreq = entradaUsuario.nextLine();
                        List<Frequencia> frequenciasAluno = controller.listarFrequenciasPorAluno(matAlunoListarFreq);

                        if (frequenciasAluno.isEmpty()) {
                            System.out.println("Nenhuma frequência encontrada para o aluno " + matAlunoListarFreq + ".");
                        } else {
                            System.out.println("Frequências do aluno " + matAlunoListarFreq + ":");
                            for (Frequencia freq : frequenciasAluno) {
                                System.out.println(freq.toString());
                            }
                        }
                        break;

                    case 5:
                        System.out.println("\n--- CALCULAR PORCENTAGEM DE FREQUÊNCIA ---");
                        System.out.print("Matrícula do Aluno: ");
                        String matAlunoCalcFreq = entradaUsuario.nextLine();
                        System.out.print("Código da Disciplina: ");
                        String codDiscCalcFreq = entradaUsuario.nextLine();
                        System.out.print("Semestre da Turma (ex: 2024.1): ");
                        String semTurmaCalcFreq = entradaUsuario.nextLine();

                        double porcentagem = controller.calcularPorcentagemFrequencia(matAlunoCalcFreq, codDiscCalcFreq, semTurmaCalcFreq);
                        if (Double.isNaN(porcentagem)) { 
                            System.out.println("Não foi possível calcular. Verifique se o aluno está matriculado nesta turma ou se há registros de frequência.");
                        } else {
                            System.out.printf("Porcentagem de frequência para o aluno %s na turma %s/%s: %.2f%%%n",
                                matAlunoCalcFreq, codDiscCalcFreq, semTurmaCalcFreq, porcentagem);
                        }
                        break;

                    case 6: // NOVA OPÇÃO: Calcular Média Final
                        System.out.println("\n--- CALCULAR MÉDIA FINAL ---");
                        System.out.print("Matrícula do Aluno: ");
                        String matAlunoCalcMedia = entradaUsuario.nextLine();
                        System.out.print("Código da Disciplina: ");
                        String codDiscCalcMedia = entradaUsuario.nextLine();
                        System.out.print("Semestre da Turma (ex: 2024.1): ");
                        String semTurmaCalcMedia = entradaUsuario.nextLine();

                        double mediaFinal = controller.calcularMediaFinal(matAlunoCalcMedia, codDiscCalcMedia, semTurmaCalcMedia);
                        if (Double.isNaN(mediaFinal)) {
                            System.out.println("Não foi possível calcular a média. Verifique os dados, se o aluno é especial ou se há notas suficientes.");
                        } else {
                            System.out.printf("Média final para o aluno %s na turma %s/%s: %.2f%n",
                                matAlunoCalcMedia, codDiscCalcMedia, semTurmaCalcMedia, mediaFinal);
                        }
                        break;

                    case 7: // NOVA OPÇÃO: Verificar Status de Aprovação
                        System.out.println("\n--- VERIFICAR STATUS DE APROVAÇÃO ---");
                        System.out.print("Matrícula do Aluno: ");
                        String matAlunoStatus = entradaUsuario.nextLine();
                        System.out.print("Código da Disciplina: ");
                        String codDiscStatus = entradaUsuario.nextLine();
                        System.out.print("Semestre da Turma (ex: 2024.1): ");
                        String semTurmaStatus = entradaUsuario.nextLine();

                        String statusAprovacao = controller.verificarStatusAprovacao(matAlunoStatus, codDiscStatus, semTurmaStatus);
                        System.out.println("Status de Aprovação para o aluno " + matAlunoStatus + " na turma " + codDiscStatus + "/" + semTurmaStatus + ": " + statusAprovacao);
                        break;

                    case 0:
                        System.out.println("Voltando ao Menu Principal...");
                        break;
                    default:
                        System.out.println("Opção inválida no Modo Avaliação/Frequência. Por favor, tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nEntrada inválida! Digite apenas números para escolher uma opção.");
                entradaUsuario.nextLine(); 
                opcaoAF = -1; // Mantém o loop
            } catch (NumberFormatException e) {
                System.out.println("\nErro de formato! Digite um número válido para notas/cálculos, ou use vírgula para decimais.");
                opcaoAF = -1;
            }
        }
    }

    /**
     * Método auxiliar para converter uma string de data (DD/MM/AAAA) para LocalDate.
     * @param dateString A string da data.
     * @return LocalDate correspondente, ou null se o formato for inválido.
     */
    private static LocalDate parseDate(String dateString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Formato de data inválido. Use DD/MM/AAAA. Erro: " + e.getMessage());
            return null;
        }
    }
}