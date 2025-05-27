package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Aluno {
    protected String nome;
    protected String matricula;
    protected String curso;
    protected boolean trancado;
    protected List<String> disciplinasMatriculadas = new ArrayList<>();

    public Aluno(String nome, String matricula, String curso) {
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
        this.trancado = false;
    }

    public String getNome() { return nome; }
    public String getMatricula() { return matricula; }
    public String getCurso() { return curso; }
    public boolean isTrancado() { return trancado; }
    public List<String> getDisciplinasMatriculadas() { return disciplinasMatriculadas; }

    public void setNome(String nome) { this.nome = nome; }
    public void setCurso(String curso) { this.curso = curso; }

    public void trancarSemestre() {
        this.trancado = true;
        this.disciplinasMatriculadas.clear();
    }

    public boolean matricularDisciplina(String codDisciplina) {
        if (trancado) return false;
        return disciplinasMatriculadas.add(codDisciplina);
    }

    public void removerDisciplina(String codDisciplina) {
        disciplinasMatriculadas.remove(codDisciplina);
    }

    public abstract boolean podeMatricularMais();
    public abstract boolean recebeNota();
}
