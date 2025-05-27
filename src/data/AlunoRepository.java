package data;

import model.*;
import java.io.*;
import java.util.*;

public class AlunoRepository {
    private List<Aluno> alunos = new ArrayList<>();
    private final String arquivo = "alunos.csv";

    public AlunoRepository() {
        carregarAlunos();
    }

    public void adicionarAluno(Aluno aluno) {
        alunos.add(aluno);
    }

    public List<Aluno> getTodosAlunos() {
        return alunos;
    }

    public Aluno buscarPorMatricula(String matricula) {
        for (Aluno a : alunos) {
            if (a.getMatricula().equals(matricula)) return a;
        }
        return null;
    }

    public void salvarAlunos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo))) {
            for (Aluno a : alunos) {
                String tipo = (a instanceof AlunoEspecial) ? "especial" : "normal";
                pw.println(tipo + ";" + a.getNome() + ";" + a.getMatricula() + ";" + a.getCurso());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar alunos: " + e.getMessage());
        }
    }

    public void carregarAlunos() {
        alunos.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                Aluno a = partes[0].equals("especial") ? new AlunoEspecial(partes[1], partes[2], partes[3])
                                                       : new AlunoNormal(partes[1], partes[2], partes[3]);
                alunos.add(a);
            }
        } catch (IOException e) {
            System.out.println("Arquivo de alunos não encontrado.");
        }
    }
}
