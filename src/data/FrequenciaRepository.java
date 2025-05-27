package data;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import model.Frequencia;

public class FrequenciaRepository {
    private static final String CAMINHO_ARQUIVO = "frequencias.csv";

    public static void salvarFrequencias(List<Frequencia> frequencias) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            for (Frequencia f : frequencias) {

                writer.write(f.getMatriculaAluno() + ";" +
                             f.getCodigoDisciplina() + ";" +
                             f.getSemestreTurma() + ";" +
                             f.getDataAula().toString() + ";" +
                             f.isPresente());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar frequências: " + e.getMessage());
        }
    }

    public static List<Frequencia> carregarFrequencias() {
        List<Frequencia> lista = new ArrayList<>();
        File arquivo = new File(CAMINHO_ARQUIVO);
        if (!arquivo.exists()) {
            System.out.println("Arquivo de frequências não encontrado. Criando um novo...");
            return lista;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 5) {
                    try {
                        String matriculaAluno = partes[0];
                        String codigoDisciplina = partes[1];
                        String semestreTurma = partes[2];
                        LocalDate dataAula = LocalDate.parse(partes[3]);
                        boolean presente = Boolean.parseBoolean(partes[4]);

                        Frequencia frequencia = new Frequencia(matriculaAluno, codigoDisciplina, semestreTurma,
                                                              dataAula, presente);
                        lista.add(frequencia);
                    } catch (DateTimeParseException e) {
                        System.out.println("Erro ao parsear linha de frequência (formato de data inválido): " + linha + " - " + e.getMessage());
                    }
                } else {
                    System.out.println("Linha de frequência com formato inválido (número de partes incorreto): " + linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar frequências: " + e.getMessage());
        }
        return lista;
    }
}