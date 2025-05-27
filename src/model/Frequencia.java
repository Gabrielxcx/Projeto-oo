package model;

import java.time.LocalDate; 

public class Frequencia {
    private String matriculaAluno;   
    private String codigoDisciplina; 
    private String semestreTurma;    
    private LocalDate dataAula;      
    private boolean presente;        

    public Frequencia(String matriculaAluno, String codigoDisciplina, String semestreTurma,
                      LocalDate dataAula, boolean presente) {
        this.matriculaAluno = matriculaAluno;
        this.codigoDisciplina = codigoDisciplina;
        this.semestreTurma = semestreTurma;
        this.dataAula = dataAula;
        this.presente = presente;
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

    public LocalDate getDataAula() {
        return dataAula;
    }

    public boolean isPresente() {
        return presente;
    }

   
    public void setPresente(boolean presente) {
        this.presente = presente;
    }

   
    @Override
    public String toString() {
        String status = presente ? "PRESENTE" : "AUSENTE";
        return "Frequência [Aluno: " + matriculaAluno +
               ", Disciplina: " + codigoDisciplina +
               ", Turma: " + semestreTurma +
               ", Data: " + dataAula +
               ", Status: " + status + "]";
    }
}