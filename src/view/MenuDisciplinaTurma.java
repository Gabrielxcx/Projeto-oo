package view;

import controller.DisciplinaController;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import model.Disciplina; // Importar model.Disciplina
import model.Turma;      // Importar model.Turma

public class MenuDisciplinaTurma {

public static void exibirMenuDisciplinaTurma(Scanner entradaUsuario) {
 DisciplinaController disciplinaController = new DisciplinaController();
int opcaoDT = -1; 

System.out.println("\n--- MODO DISCIPLINA E TURMA ---");

 while (opcaoDT != 0) {
System.out.println("\n===== Menu Disciplina/Turma =====");
System.out.println("1. Cadastrar Nova Disciplina");
System.out.println("2. Listar Todas as Disciplinas");
System.out.println("3. Criar Nova Turma");
System.out.println("4. Listar Todas as Turmas");
 System.out.println("5. Matricular Aluno em Turma"); 
 System.out.println("0. Voltar ao Menu Principal");
System.out.print("Sua escolha no Modo Disciplina/Turma: ");

try {
 opcaoDT = entradaUsuario.nextInt();
entradaUsuario.nextLine(); 

 switch (opcaoDT) {
case 1:
System.out.println("\n--- CADASTRO DE DISCIPLINA ---");
 System.out.print("Nome da Disciplina: ");
 String nomeDisciplina = entradaUsuario.nextLine();
System.out.print("Código da Disciplina (ex: CD101): ");
String codigoDisciplina = entradaUsuario.nextLine();
 System.out.print("Carga Horária (em horas, apenas números): ");
int cargaHoraria = entradaUsuario.nextInt();
entradaUsuario.nextLine(); 

Disciplina novaDisciplina = new Disciplina(nomeDisciplina, codigoDisciplina, cargaHoraria);

System.out.print("Adicionar pré-requisitos? (sim/nao): ");
String respostaPrereq = entradaUsuario.nextLine();
if (respostaPrereq.equalsIgnoreCase("sim")) {
System.out.println("Digite os códigos dos pré-requisitos, um por linha. Digite 'fim' para terminar.");
String prereqCodigo;
 while (!(prereqCodigo = entradaUsuario.nextLine()).equalsIgnoreCase("fim")) {
novaDisciplina.adicionarPreRequisito(prereqCodigo);
 System.out.print("Próximo pré-requisito (ou 'fim'): ");
 }
}

disciplinaController.adicionarDisciplina(novaDisciplina);
System.out.println("Disciplina '" + nomeDisciplina + "' cadastrada com sucesso!");
break;

case 2:
System.out.println("\n--- LISTA DE DISCIPLINAS CADASTRADAS ---");
List<Disciplina> disciplinas = disciplinaController.getDisciplinas();
if (disciplinas.isEmpty()) {
System.out.println("Nenhuma disciplina cadastrada ainda.");
} else {
 for (Disciplina d : disciplinas) {
 System.out.println(d.toString());
}
 }
break;

 case 3:
System.out.println("\n--- CRIAÇÃO DE TURMA ---");
String discCodigoTurma = entradaUsuario.nextLine();

System.out.print("Nome do Professor: ");
String professor = entradaUsuario.nextLine();
System.out.print("Semestre da Turma (ex: 2024.1): ");
String semestre = entradaUsuario.nextLine();
System.out.print("Forma de Avaliação: ");
 String formaAvaliacao = entradaUsuario.nextLine();
System.out.print("A turma é remota? (sim/nao): ");
boolean remota = entradaUsuario.nextLine().equalsIgnoreCase("sim");

String sala = "";
if (!remota) {
 System.out.print("Sala da Turma: ");
sala = entradaUsuario.nextLine();
} else {
 sala = "Online"; 
 }
System.out.print("Horário da Turma (ex: SEG 10:00-12:00): ");
String horario = entradaUsuario.nextLine();
 System.out.print("Capacidade Máxima de Alunos: ");
 int capacidadeMax = entradaUsuario.nextInt();
 entradaUsuario.nextLine(); 

Turma novaTurma = new Turma(discCodigoTurma, professor, semestre,
 formaAvaliacao, remota, sala, horario, capacidadeMax);

 disciplinaController.adicionarTurma(novaTurma); 
System.out.println("Turma para a disciplina '" + discCodigoTurma + "' criada!");
break;

case 4:
System.out.println("\n--- LISTA DE TURMAS CADASTRADAS ---");
 List<Turma> turmas = disciplinaController.getTurmas();
if (turmas.isEmpty()) {
System.out.println("Nenhuma turma cadastrada ainda.");
} else {
 for (Turma t : turmas) {
System.out.println(t.toString());
}
}
 break;

                    case 5: // Matricular Aluno em Turma
                        System.out.println("\n--- MATRICULAR ALUNO EM TURMA ---");
                        System.out.print("Matrícula do Aluno: ");
                        String matAluno = entradaUsuario.nextLine();
                        System.out.print("Código da Disciplina da Turma: ");
                        String codDisc = entradaUsuario.nextLine();
                        System.out.print("Semestre da Turma (ex: 2024.1): ");
                        String semTurma = entradaUsuario.nextLine();

                        if (disciplinaController.matricularAlunoEmTurma(matAluno, codDisc, semTurma)) {
                            System.out.println("Aluno '" + matAluno + "' matriculado com sucesso na turma de " + codDisc + " (" + semTurma + ").");
                        } else {
                            System.out.println("Falha ao matricular aluno. Verifique os dados fornecidos e as regras de matrícula.");
                        }
                        break; // Fim do case 5

case 0:
System.out.println("Voltando ao Menu Principal...");
break;
default:
System.out.println("Opção inválida no Modo Disciplina/Turma. Por favor, tente novamente.");
}
 } catch (InputMismatchException e) {
 System.out.println("\nEntrada inválida! Digite apenas números para escolher uma opção.");
 entradaUsuario.nextLine(); 
 opcaoDT = -1; 
} catch (NumberFormatException e) { 
System.out.println("\nErro de formato. Certifique-se de digitar números onde é pedido.");
entradaUsuario.nextLine(); 
opcaoDT = -1; 
 }
}
}
}