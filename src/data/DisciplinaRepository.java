package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Disciplina;

public class DisciplinaRepository {
    private static final String CAMINHO_ARQUIVO = "disciplinas.txt";

    // Salva todas as disciplinas no arquivo
    public static void salvarDisciplinas(List<Disciplina> disciplinas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            for (Disciplina d : disciplinas) {
                // Formato: nome;codigo;cargaHoraria;pre1,pre2,...
                writer.write(
                    d.getNome() + ";" +
                    d.getCodigo() + ";" +
                    d.getCargaHoraria() + ";" +
                    String.join(",", d.getPreRequisitos())
                );
                writer.newLine(); // quebra de linha para cada disciplina
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar disciplinas: " + e.getMessage());
        }
    }

    // Carrega todas as disciplinas do arquivo
    public static List<Disciplina> carregarDisciplinas() {
        List<Disciplina> lista = new ArrayList<>();
        File arquivo = new File(CAMINHO_ARQUIVO);

        if (!arquivo.exists()) return lista; // Retorna lista vazia se não houver arquivo

        try (BufferedReader reader = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 3) {
                    Disciplina d = new Disciplina(partes[0], partes[1], Integer.parseInt(partes[2]));

                    // Verifica se há pré-requisitos
                    if (partes.length > 3 && !partes[3].isEmpty()) {
                        for (String prereq : partes[3].split(",")) {
                            d.adicionarPreRequisito(prereq);
                        }
                    }

                    lista.add(d);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar disciplinas: " + e.getMessage());
        }

        return lista;
    }
}
