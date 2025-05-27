package model;

import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    private String nome;
    private String codigo;
    private int cargaHoraria;
    private List<String> preRequisitos;

    public Disciplina(String nome, String codigo, int cargaHoraria) {
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
        this.preRequisitos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public List<String> getPreRequisitos() {
        return preRequisitos;
    }

    public void adicionarPreRequisito(String codigo) {
        if (!preRequisitos.contains(codigo)) {
            preRequisitos.add(codigo);
        }
    }

    @Override
    public String toString() {
        return "Disciplina: " + nome + " (" + codigo + "), Carga Horária: " + cargaHoraria + ", Pré-requisitos: " + preRequisitos;
    }
}
