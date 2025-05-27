package model;

public class AlunoNormal extends Aluno {
    public AlunoNormal(String nome, String matricula, String curso) {
        super(nome, matricula, curso);
    }

    @Override
    public boolean podeMatricularMais() {
        return true;
    }

    @Override
    public boolean recebeNota() {
        return true;
    }
}
