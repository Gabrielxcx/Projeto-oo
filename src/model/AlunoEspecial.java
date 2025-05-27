package model;

public class AlunoEspecial extends Aluno {
    public AlunoEspecial(String nome, String matricula, String curso) {
        super(nome, matricula, curso);
    }

    @Override
    public boolean podeMatricularMais() {
        return disciplinasMatriculadas.size() < 2;
    }

    @Override
    public boolean recebeNota() {
        return false;
    }
}
