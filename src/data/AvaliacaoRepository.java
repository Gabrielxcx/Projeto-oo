package data;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException; 
import java.util.ArrayList;
import java.util.List;
import model.Avaliacao;

public class AvaliacaoRepository {
    private static final String CAMINHO_ARQUIVO = "avaliacoes.csv";

    public static void salvarAvaliacoes(List<Avaliacao> avaliacoes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            for (Avaliacao a : avaliacoes) {
                
                writer.write(a.getMatriculaAluno() + ";" +
                             a.getCodigoDisciplina() + ";" +
                             a.getSemestreTurma() + ";" +
                             a.getTipoAvaliacao() + ";" +
                             String.format("%.2f", a.getNota()) + ";" + 
                             a.getDataAvaliacao().toString()); 
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar avaliações: " + e.getMessage());
        }
    }

    public static List<Avaliacao> carregarAvaliacoes() {
        List<Avaliacao> lista = new ArrayList<>();
        File arquivo = new File(CAMINHO_ARQUIVO);
        if (!arquivo.exists()) {
            System.out.println("Arquivo de avaliações não encontrado. Criando um novo...");
            return lista; 
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 6) { // 6 partes (aluno, disc, turma, tipo, nota, data)
                    try {
                        String matriculaAluno = partes[0];
                        String codigoDisciplina = partes[1];
                        String semestreTurma = partes[2];
                        String tipoAvaliacao = partes[3];
                        double nota = Double.parseDouble(partes[4].replace(",", ".")); 
                        LocalDate dataAvaliacao = LocalDate.parse(partes[5]); 

                        Avaliacao avaliacao = new Avaliacao(matriculaAluno, codigoDisciplina, semestreTurma,
                                                            tipoAvaliacao, nota, dataAvaliacao);
                        lista.add(avaliacao);
                    } catch (NumberFormatException | DateTimeParseException e) {
                        System.out.println("Erro ao parsear linha de avaliação (formato inválido): " + linha + " - " + e.getMessage());
                    }
                } else {
                    System.out.println("Linha de avaliação com formato inválido (número de partes incorreto): " + linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar avaliações: " + e.getMessage());
        }
        return lista;
    }
}