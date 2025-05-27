package data;

import model.Turma;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TurmaRepository {
    private static final String CAMINHO_ARQUIVO = "turmas.txt";

    public static void salvarTurmas(List<Turma> turmas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            for (Turma t : turmas) {
                writer.write(t.getDisciplinaCodigo() + ";" + t.getProfessor() + ";" + t.getSemestre() + ";" +
                        t.getFormaAvaliacao() + ";" + t.isRemota() + ";" + t.getSala() + ";" +
                        t.getHorario() + ";" + t.getCapacidadeMaxima() + ";" +
                        String.join(",", t.getMatriculasAlunos()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar turmas: " + e.getMessage());
        }
    }

    public static List<Turma> carregarTurmas() {
        List<Turma> lista = new ArrayList<>();
        File arquivo = new File(CAMINHO_ARQUIVO);
        if (!arquivo.exists()) return lista;

        try (BufferedReader reader = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                Turma t = new Turma(
                        partes[0],
                        partes[1],
                        partes[2],
                        partes[3],
                        Boolean.parseBoolean(partes[4]),
                        partes[5],
                        partes[6],
                        Integer.parseInt(partes[7])
                );
                if (partes.length > 8 && !partes[8].isEmpty()) {
                    for (String matricula : partes[8].split(",")) {
                        t.adicionarAluno(matricula);
                    }
                }
                lista.add(t);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar turmas: " + e.getMessage());
        }

        return lista;
    }
}
