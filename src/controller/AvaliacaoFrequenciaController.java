package controller;

import data.AlunoRepository;
import data.AvaliacaoRepository;
import data.DisciplinaRepository;
import data.FrequenciaRepository;
import data.TurmaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import model.Aluno;
import model.AlunoEspecial;
import model.Avaliacao; // Import necessário para AlunoEspecial
import model.Disciplina;
import model.Frequencia;
import model.Turma;

public class AvaliacaoFrequenciaController {
    private List<Avaliacao> avaliacoes;
    private List<Frequencia> frequencias;
    
    private AlunoRepository alunoRepo;

    public AvaliacaoFrequenciaController() {
        avaliacoes = AvaliacaoRepository.carregarAvaliacoes();
        frequencias = FrequenciaRepository.carregarFrequencias();
        alunoRepo = new AlunoRepository(); 
    }

    // --- Métodos para Avaliações ---

    public boolean lancarNota(String matriculaAluno, String codigoDisciplina, String semestreTurma,
                              String tipoAvaliacao, double nota, LocalDate dataAvaliacao) {
        // verificar se aluno, disciplina e turma existem
        Aluno aluno = alunoRepo.buscarPorMatricula(matriculaAluno);
        List<Disciplina> disciplinasExistentes = DisciplinaRepository.carregarDisciplinas(); 
        List<Turma> turmasExistentes = TurmaRepository.carregarTurmas(); 

        boolean alunoExiste = (aluno != null);
        boolean disciplinaExiste = disciplinasExistentes.stream()
                                           .anyMatch(d -> d.getCodigo().equalsIgnoreCase(codigoDisciplina));
        boolean turmaExiste = turmasExistentes.stream()
                                   .anyMatch(t -> t.getDisciplinaCodigo().equalsIgnoreCase(codigoDisciplina) &&
                                                  t.getSemestre().equalsIgnoreCase(semestreTurma) &&
                                                  t.getMatriculasAlunos().contains(matriculaAluno));

        if (!alunoExiste) {
            System.out.println("Erro: Aluno com matrícula " + matriculaAluno + " não encontrado.");
            return false;
        }
        if (!disciplinaExiste) {
            System.out.println("Erro: Disciplina " + codigoDisciplina + " não encontrada.");
            return false;
        }
        if (!turmaExiste) {
            System.out.println("Erro: Turma de " + codigoDisciplina + " no semestre " + semestreTurma + " não encontrada ou aluno não matriculado nela.");
            return false;
        }

        Avaliacao novaAvaliacao = new Avaliacao(matriculaAluno, codigoDisciplina, semestreTurma, tipoAvaliacao, nota, dataAvaliacao);
        avaliacoes.add(novaAvaliacao);
        AvaliacaoRepository.salvarAvaliacoes(avaliacoes);
        return true;
    }

    public List<Avaliacao> listarAvaliacoesPorAluno(String matriculaAluno) {
        return avaliacoes.stream()
                         .filter(a -> a.getMatriculaAluno().equalsIgnoreCase(matriculaAluno))
                         .collect(Collectors.toList());
    }

    public List<Avaliacao> listarAvaliacoesPorDisciplinaETurma(String codigoDisciplina, String semestreTurma) {
        return avaliacoes.stream()
                         .filter(a -> a.getCodigoDisciplina().equalsIgnoreCase(codigoDisciplina) &&
                                       a.getSemestreTurma().equalsIgnoreCase(semestreTurma))
                         .collect(Collectors.toList());
    }

    // --- Métodos para Frequências ---

    public boolean registrarFrequencia(String matriculaAluno, String codigoDisciplina, String semestreTurma,
                                       LocalDate dataAula, boolean presente) {
        
        Aluno aluno = alunoRepo.buscarPorMatricula(matriculaAluno);
        List<Disciplina> disciplinasExistentes = DisciplinaRepository.carregarDisciplinas();
        List<Turma> turmasExistentes = TurmaRepository.carregarTurmas();

        boolean alunoExiste = (aluno != null);
        boolean disciplinaExiste = disciplinasExistentes.stream()
                                           .anyMatch(d -> d.getCodigo().equalsIgnoreCase(codigoDisciplina));
        boolean turmaExiste = turmasExistentes.stream()
                                   .anyMatch(t -> t.getDisciplinaCodigo().equalsIgnoreCase(codigoDisciplina) &&
                                                  t.getSemestre().equalsIgnoreCase(semestreTurma) &&
                                                  t.getMatriculasAlunos().contains(matriculaAluno));

        if (!alunoExiste) {
            System.out.println("Erro: Aluno com matrícula " + matriculaAluno + " não encontrado.");
            return false;
        }
        if (!disciplinaExiste) {
            System.out.println("Erro: Disciplina " + codigoDisciplina + " não encontrada.");
            return false;
        }
        if (!turmaExiste) {
            System.out.println("Erro: Turma de " + codigoDisciplina + " no semestre " + semestreTurma + " não encontrada ou aluno não matriculado nela.");
            return false;
        }

        // Validação
        boolean registroExistente = frequencias.stream()
            .anyMatch(f -> f.getMatriculaAluno().equalsIgnoreCase(matriculaAluno) &&
                           f.getCodigoDisciplina().equalsIgnoreCase(codigoDisciplina) &&
                           f.getSemestreTurma().equalsIgnoreCase(semestreTurma) &&
                           f.getDataAula().equals(dataAula));

        if (registroExistente) {
            System.out.println("Erro: Frequência para esta aula (" + dataAula + ") e aluno já registrada.");
            return false;
        }

        Frequencia novaFrequencia = new Frequencia(matriculaAluno, codigoDisciplina, semestreTurma,
                                                     dataAula, presente);
        frequencias.add(novaFrequencia);
        FrequenciaRepository.salvarFrequencias(frequencias);
        return true;
    }

    public List<Frequencia> listarFrequenciasPorAluno(String matriculaAluno) {
        return frequencias.stream()
                          .filter(f -> f.getMatriculaAluno().equalsIgnoreCase(matriculaAluno))
                          .collect(Collectors.toList());
    }

    public List<Frequencia> listarFrequenciasPorDisciplinaETurma(String codigoDisciplina, String semestreTurma) {
        return frequencias.stream()
                          .filter(f -> f.getCodigoDisciplina().equalsIgnoreCase(codigoDisciplina) &&
                                       f.getSemestreTurma().equalsIgnoreCase(semestreTurma))
                          .collect(Collectors.toList());
    }

    public List<Frequencia> listarFrequenciasPorTurmaEData(String codigoDisciplina, String semestreTurma, LocalDate dataAula) {
        return frequencias.stream()
                          .filter(f -> f.getCodigoDisciplina().equalsIgnoreCase(codigoDisciplina) &&
                                       f.getSemestreTurma().equalsIgnoreCase(semestreTurma) &&
                                       f.getDataAula().equals(dataAula))
                          .collect(Collectors.toList());
    }

    // Método para calcular o total de presenças
    public double calcularPorcentagemFrequencia(String matriculaAluno, String codigoDisciplina, String semestreTurma) {
        List<Frequencia> freqAlunoTurma = frequencias.stream()
            .filter(f -> f.getMatriculaAluno().equalsIgnoreCase(matriculaAluno) &&
                         f.getCodigoDisciplina().equalsIgnoreCase(codigoDisciplina) &&
                         f.getSemestreTurma().equalsIgnoreCase(semestreTurma))
            .collect(Collectors.toList());

        if (freqAlunoTurma.isEmpty()) {
            return 0.0;
        }

        long totalPresencas = freqAlunoTurma.stream().filter(Frequencia::isPresente).count();
        return (double) totalPresencas / freqAlunoTurma.size() * 100.0;
    }

    // --- Métodos de Cálculo de Média Final e Status de Aprovação ---

    private String getFormaAvaliacaoDaTurma(String codigoDisciplina, String semestreTurma) {
        List<Turma> turmasExistentes = TurmaRepository.carregarTurmas();
        return turmasExistentes.stream()
                                .filter(t -> t.getDisciplinaCodigo().equalsIgnoreCase(codigoDisciplina) &&
                                             t.getSemestre().equalsIgnoreCase(semestreTurma))
                                .map(Turma::getFormaAvaliacao)
                                .findFirst()
                                .orElse(null);
    }

    private double getNotaAvaliacao(List<Avaliacao> avaliacoesDoAlunoNaTurma, String tipoAvaliacao) {
        return avaliacoesDoAlunoNaTurma.stream()
                                        .filter(a -> a.getTipoAvaliacao().equalsIgnoreCase(tipoAvaliacao))
                                        .mapToDouble(Avaliacao::getNota)
                                        .findFirst()
                                        .orElse(0.0);
    }

    public double calcularMediaFinal(String matriculaAluno, String codigoDisciplina, String semestreTurma) {
        String formaAvaliacao = getFormaAvaliacaoDaTurma(codigoDisciplina, semestreTurma);
        if (formaAvaliacao == null) {
            System.out.println("Erro ao calcular média: Turma não encontrada ou forma de avaliação não definida.");
            return Double.NaN;
        }

        // Verificação para AlunoEspecial - Adicionada aqui.
        Aluno aluno = alunoRepo.buscarPorMatricula(matriculaAluno); // Buscando o aluno
        if (aluno instanceof AlunoEspecial) { 
            // Aluno Especial não tem média de notas, apenas frequência
            System.out.println("Aluno Especial não possui média final por nota.");
            return Double.NaN; 
        }

        List<Avaliacao> avaliacoesDoAlunoNaTurma = avaliacoes.stream()
            .filter(a -> a.getMatriculaAluno().equalsIgnoreCase(matriculaAluno) &&
                         a.getCodigoDisciplina().equalsIgnoreCase(codigoDisciplina) &&
                         a.getSemestreTurma().equalsIgnoreCase(semestreTurma))
            .collect(Collectors.toList());

        double p1 = getNotaAvaliacao(avaliacoesDoAlunoNaTurma, "P1");
        double p2 = getNotaAvaliacao(avaliacoesDoAlunoNaTurma, "P2");
        double p3 = getNotaAvaliacao(avaliacoesDoAlunoNaTurma, "P3");
        double l = getNotaAvaliacao(avaliacoesDoAlunoNaTurma, "L");
        double s = getNotaAvaliacao(avaliacoesDoAlunoNaTurma, "S");

        if (formaAvaliacao.equalsIgnoreCase("Media Final = (P1 + P2 + P3 + L + S) / 5")) {
            return (p1 + p2 + p3 + l + s) / 5.0;
        } else if (formaAvaliacao.equalsIgnoreCase("Media Final = (P1 + P2 * 2 + P3 * 3 + L + S) / 8")) {
            return (p1 + (p2 * 2) + (p3 * 3) + l + s) / 8.0;
        } else {
            System.out.println("Forma de avaliação '" + formaAvaliacao + "' desconhecida.");
            return Double.NaN;
        }
    }

    public String verificarStatusAprovacao(String matriculaAluno, String codigoDisciplina, String semestreTurma) {
        // Validação para AlunoEspecial (não recebe nota, apenas presença)
        Aluno aluno = alunoRepo.buscarPorMatricula(matriculaAluno);
        if (aluno instanceof AlunoEspecial) {
            double porcentagemFrequencia = calcularPorcentagemFrequencia(matriculaAluno, codigoDisciplina, semestreTurma);
            if (Double.isNaN(porcentagemFrequencia) || porcentagemFrequencia < 75.0) {
                return "REPROVADO POR FALTA (Aluno Especial)";
            } else {
                return "APROVADO (Aluno Especial)";
            }
        }

        // Para Alunos Normais: nota E frequência
        double mediaFinal = calcularMediaFinal(matriculaAluno, codigoDisciplina, semestreTurma);
        double porcentagemFrequencia = calcularPorcentagemFrequencia(matriculaAluno, codigoDisciplina, semestreTurma);

        if (Double.isNaN(mediaFinal) || Double.isNaN(porcentagemFrequencia)) {
            return "IMPOSSÍVEL AVALIAR (Dados insuficientes ou turma não encontrada)";
        }

        if (porcentagemFrequencia < 75.0) {
            return "REPROVADO POR FALTA";
        }

        if (mediaFinal >= 5.0) {
            return "APROVADO";
        } else {
            return "REPROVADO POR NOTA";
        }
    }
}