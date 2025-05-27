package controller;

import data.AlunoRepository;
import data.DisciplinaRepository;
import data.TurmaRepository;
import java.util.List; 
import model.Aluno;
import model.AlunoEspecial;
import model.Disciplina;
import model.Turma;

public class DisciplinaController {
    private List<Disciplina> disciplinas;
    private List<Turma> turmas;
    private AlunoRepository alunoRepo; 

    public DisciplinaController() {
        this.disciplinas = DisciplinaRepository.carregarDisciplinas();
        this.turmas = TurmaRepository.carregarTurmas();
        this.alunoRepo = new AlunoRepository();
    }

  
    public void adicionarDisciplina(Disciplina d) {
        
        disciplinas.add(d);
        DisciplinaRepository.salvarDisciplinas(disciplinas);
    }

    
    public void adicionarTurma(Turma t) {
        
        for (Turma turmaExistente : turmas) {
            if (turmaExistente.getDisciplinaCodigo().equalsIgnoreCase(t.getDisciplinaCodigo()) &&
                turmaExistente.getHorario().equalsIgnoreCase(t.getHorario())) {
                System.out.println("Já existe uma turma dessa disciplina nesse horário!");
                return;
            }
        }
        turmas.add(t);
        TurmaRepository.salvarTurmas(turmas);
    }


    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    // Retorna a lista atual de turmas
    public List<Turma> getTurmas() {
        return turmas;
    }

    
    public boolean matricularAlunoEmTurma(String matriculaAluno, String codigoDisciplina, String semestreTurma) {
       
        Aluno aluno = alunoRepo.buscarPorMatricula(matriculaAluno);
        if (aluno == null) {
            System.out.println("Erro: Aluno com matrícula '" + matriculaAluno + "' não encontrado.");
            return false;
        }

       
        Turma turmaParaMatricular = null;
        for (Turma t : turmas) {
            if (t.getDisciplinaCodigo().equalsIgnoreCase(codigoDisciplina) &&
                t.getSemestre().equalsIgnoreCase(semestreTurma)) {
                turmaParaMatricular = t;
                break;
            }
        }

        if (turmaParaMatricular == null) {
            System.out.println("Erro: Turma de '" + codigoDisciplina + "' no semestre '" + semestreTurma + "' não encontrada.");
            return false;
        }

       
        if (aluno instanceof AlunoEspecial) {
            long turmasMatriculadas = turmas.stream()
                                            .filter(t -> t.getMatriculasAlunos().contains(matriculaAluno))
                                            .count();
            if (turmasMatriculadas >= 2) {
                System.out.println("Erro: Aluno Especial '" + matriculaAluno + "' já está matriculado em 2 disciplinas. Não pode se matricular em mais.");
                return false;
            }
        }
        
       
        if (turmaParaMatricular.adicionarAluno(matriculaAluno)) {
            TurmaRepository.salvarTurmas(turmas); 
            return true;
        } else {
            System.out.println("Falha ao matriculaar aluno: Turma cheia ou aluno já matriculado.");
            return false;
        }
    }
}