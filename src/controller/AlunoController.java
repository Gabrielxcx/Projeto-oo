package controller;

import data.AlunoRepository;
import java.util.List;
import model.*;

public class AlunoController {
    private AlunoRepository repo;

    public AlunoController() {
        repo = new AlunoRepository();
    }

    public boolean cadastrarAluno(String nome, String matricula, String curso, boolean especial) {
        if (repo.buscarPorMatricula(matricula) != null) return false;
        Aluno aluno = especial ? new AlunoEspecial(nome, matricula, curso)
                               : new AlunoNormal(nome, matricula, curso);
        repo.adicionarAluno(aluno);
        repo.salvarAlunos();
        return true;
    }

    public boolean editarAluno(String matricula, String novoNome, String novoCurso) {
        // buscar o aluno
        Aluno aluno = buscarAlunoPorMatricula(matricula);
        if (aluno == null) return false;
        aluno.setNome(novoNome);
        aluno.setCurso(novoCurso);
        repo.salvarAlunos();
        return true;
    }

    public boolean trancarAluno(String matricula) {
        // buscar o aluno
        Aluno aluno = buscarAlunoPorMatricula(matricula);
        if (aluno == null) return false;
        aluno.trancarSemestre();
        repo.salvarAlunos();
        return true;
    }

    public List<Aluno> listarAlunos() {
        return repo.getTodosAlunos();
    }

    // buscar aluno por matrícula
    public Aluno buscarAlunoPorMatricula(String matricula) {
        return repo.buscarPorMatricula(matricula);
    }
}