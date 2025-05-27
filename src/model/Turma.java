package model;

import java.util.ArrayList;
import java.util.List;

public class Turma {//atributos
    private String disciplinaCodigo;
    private String professor;
    private String semestre;
    private String formaAvaliacao;
    private boolean remota;
    private String sala;
    private String horario;
    private int capacidadeMaxima;
    private List<String> matriculasAlunos;
//construtor
    public Turma(String disciplinaCodigo, String professor, String semestre, String formaAvaliacao,
                 boolean remota, String sala, String horario, int capacidadeMaxima) {
        this.disciplinaCodigo = disciplinaCodigo;
        this.professor = professor;
        this.semestre = semestre;
        this.formaAvaliacao = formaAvaliacao;
        this.remota = remota;
        this.sala = remota ? "" : sala;
        this.horario = horario;
        this.capacidadeMaxima = capacidadeMaxima;
        this.matriculasAlunos = new ArrayList<>();
    }
//metodo
    public String getDisciplinaCodigo() {
        return disciplinaCodigo;
    }

    public String getProfessor() {
        return professor;
    }

    public String getSemestre() {
        return semestre;
    }

    public String getFormaAvaliacao() {
        return formaAvaliacao;
    }

    public boolean isRemota() {
        return remota;
    }

    public String getSala() {
        return sala;
    }

    public String getHorario() {
        return horario;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public List<String> getMatriculasAlunos() {
        return matriculasAlunos;
    }
//metodo
    public boolean adicionarAluno(String matricula) {
        if (matriculasAlunos.size() < capacidadeMaxima && !matriculasAlunos.contains(matricula)) {
            matriculasAlunos.add(matricula);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Turma de " + disciplinaCodigo + " com " + professor + " - " + (remota ? "Remota" : "Presencial")
                + " | Sala: " + (remota ? "N/A" : sala)
                + " | Horário: " + horario + " | Vagas: " + (capacidadeMaxima - matriculasAlunos.size());
    }
}
