package model;

import java.time.LocalDate;

public class Avaliacao {
    private String matriculaAluno; 
    private String codigoDisciplina; 
    private String semestreTurma; 
    private String tipoAvaliacao; 
    private double nota;
    private LocalDate dataAvaliacao; 

    public Avaliacao(String matriculaAluno, String codigoDisciplina, String semestreTurma,
                     String tipoAvaliacao, double nota, LocalDate dataAvaliacao) {
        this.matriculaAluno = matriculaAluno;
        this.codigoDisciplina = codigoDisciplina;
        this.semestreTurma = semestreTurma;
        this.tipoAvaliacao = tipoAvaliacao;
        this.nota = nota;
        this.dataAvaliacao = dataAvaliacao;
    }

    // --- Getters ---
    public String getMatriculaAluno() {
        return matriculaAluno;
    }

    public String getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public String getSemestreTurma() {
        return semestreTurma;
    }

    public String getTipoAvaliacao() {
        return tipoAvaliacao;
    }

    public double getNota() {
        return nota;
    }

    public LocalDate getDataAvaliacao() {
        return dataAvaliacao;
    }

   
    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Avaliação [Aluno: " + matriculaAluno +
               ", Disciplina: " + codigoDisciplina +
               ", Turma: " + semestreTurma +
               ", Tipo: " + tipoAvaliacao +
               ", Nota: " + String.format("%.2f", nota) +
               ", Data: " + dataAvaliacao + "]";
    }
}