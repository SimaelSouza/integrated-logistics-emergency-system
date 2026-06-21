package performance;

import br.com.logisticsystem.algorithms.efficient.MergeSort;
import br.com.logisticsystem.algorithms.efficient.QuickSort;
import br.com.logisticsystem.algorithms.linear.BucketSort;
import br.com.logisticsystem.algorithms.linear.CountingSort;
import br.com.logisticsystem.algorithms.linear.RadixSort;
import br.com.logisticsystem.algorithms.quadratic.BubbleSort;
import br.com.logisticsystem.algorithms.quadratic.InsertionSort;
import br.com.logisticsystem.algorithms.quadratic.SelectionSort;
import br.com.logisticsystem.utils.Estatisticas;
import br.com.logisticsystem.utils.GeradorDados;
import br.com.logisticsystem.utils.Timer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class BenchmarkTest {

    private static final String CAMINHO_ARQUIVO = "docs/benchmark-results.md";
    private static final DateTimeFormatter DATA_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    private static final List<String> secoesRelatorio = new ArrayList<>();

    private Timer timer;

    @BeforeEach
    void setUp() {
        timer = new Timer();
    }


    @AfterAll
    static void gerarArquivoFinal() {
        salvarRelatorio();
    }

    @Test
    void deveCompararTodosOsAlgoritmosComN100() {
        executarBenchmarkCompleto(100);
    }

    @Test
    void deveCompararTodosOsAlgoritmosComN1000() {
        executarBenchmarkCompleto(1000);
    }

    @Test
    void deveCompararTodosOsAlgoritmosComN10000() {
        executarBenchmarkCompleto(10000);
    }

    @Test
    void deveMostrarPiorCasoVsMelhorCaso() {
        int n = 5000;

        Estatisticas estMelhorBubble = medir("BubbleSort - Melhor Caso (ordenado)",
                GeradorDados.gerarArrayOrdenado(n), this::ordenarBubble);
        Estatisticas estPiorBubble = medir("BubbleSort - Pior Caso (invertido)",
                GeradorDados.gerarArrayInvertido(n), this::ordenarBubble);

        Estatisticas estMelhorInsertion = medir("InsertionSort - Melhor Caso (ordenado)",
                GeradorDados.gerarArrayOrdenado(n), this::ordenarInsertion);
        Estatisticas estPiorInsertion = medir("InsertionSort - Pior Caso (invertido)",
                GeradorDados.gerarArrayInvertido(n), this::ordenarInsertion);

        Estatisticas estMelhorSelection = medir("SelectionSort - Melhor Caso (ordenado)",
                GeradorDados.gerarArrayOrdenado(n), this::ordenarSelection);
        Estatisticas estPiorSelection = medir("SelectionSort - Pior Caso (invertido)",
                GeradorDados.gerarArrayInvertido(n), this::ordenarSelection);

        StringBuilder secao = new StringBuilder();
        secao.append("## Melhor Caso vs Pior Caso (n=").append(n).append(")\n\n");
        secao.append(tabelaCabecalho());
        secao.append(linhaTabela(estMelhorBubble));
        secao.append(linhaTabela(estPiorBubble));
        secao.append(linhaTabela(estMelhorInsertion));
        secao.append(linhaTabela(estPiorInsertion));
        secao.append(linhaTabela(estMelhorSelection));
        secao.append(linhaTabela(estPiorSelection));
        secao.append("\n");

        secoesRelatorio.add(secao.toString());
    }


    private void executarBenchmarkCompleto(int n) {
        int[] arrayBase = GeradorDados.gerarArrayInteiros(n);

        List<Estatisticas> resultados = new ArrayList<>();

        resultados.add(medir("BubbleSort", copiar(arrayBase), this::ordenarBubble));
        resultados.add(medir("InsertionSort", copiar(arrayBase), this::ordenarInsertion));
        resultados.add(medir("SelectionSort", copiar(arrayBase), this::ordenarSelection));
        resultados.add(medir("MergeSort", copiar(arrayBase), this::ordenarMerge));
        resultados.add(medir("QuickSort", copiar(arrayBase), this::ordenarQuick));
        resultados.add(medir("CountingSort", copiar(arrayBase), this::ordenarCounting));
        resultados.add(medir("RadixSort", copiar(arrayBase), this::ordenarRadix));
        resultados.add(medir("BucketSort", copiar(arrayBase), this::ordenarBucket));

        StringBuilder secao = new StringBuilder();
        secao.append("## Comparação Geral — n=").append(n).append("\n\n");
        secao.append(tabelaCabecalho());

        for (Estatisticas est : resultados) {
            secao.append(linhaTabela(est));
        }

        secao.append("\n");
        secoesRelatorio.add(secao.toString());
    }


    private Estatisticas medir(String nome, int[] array, AlgoritmoOrdenacao algoritmo) {
        Estatisticas estatisticas = new Estatisticas(nome, array.length);

        timer.iniciar();
        algoritmo.ordenar(array, estatisticas);
        timer.finalizar();

        estatisticas.setTempo(timer.getTempoNanosegundos());
        timer.resetar();

        return estatisticas;
    }


    private void ordenarBubble(int[] array, Estatisticas estatisticas) {
        BubbleSort.ordenar(array, estatisticas);
    }

    private void ordenarInsertion(int[] array, Estatisticas estatisticas) {
        InsertionSort.ordenar(array, estatisticas);
    }

    private void ordenarSelection(int[] array, Estatisticas estatisticas) {
        SelectionSort.ordenar(array, estatisticas);
    }

    private void ordenarMerge(int[] array, Estatisticas estatisticas) {
        MergeSort.ordenar(array, estatisticas);
    }

    private void ordenarQuick(int[] array, Estatisticas estatisticas) {
        QuickSort.ordenar(array, estatisticas);
    }

    private void ordenarCounting(int[] array, Estatisticas estatisticas) {
        CountingSort.ordenar(array, estatisticas);
    }

    private void ordenarRadix(int[] array, Estatisticas estatisticas) {
        RadixSort.ordenar(array, estatisticas);
    }

    private void ordenarBucket(int[] array, Estatisticas estatisticas) {
        BucketSort.ordenar(array, estatisticas);
    }

    @FunctionalInterface
    private interface AlgoritmoOrdenacao {
        void ordenar(int[] array, Estatisticas estatisticas);
    }


    private static String tabelaCabecalho() {
        return "| Algoritmo | n | Comparações | Trocas | Tempo |\n"
                + "|---|---|---|---|---|\n";
    }

    private static String linhaTabela(Estatisticas est) {
        String tempoFormatado = est.getTempo() < 1_000_000
                ? est.getTempo() + " ns"
                : String.format("%.2f ms", est.getTempoMilissegundos());

        return "| " + est.getNomeAlgoritmo()
                + " | " + est.getTamanhoEntrada()
                + " | " + formatarNumero(est.getComparacoes())
                + " | " + formatarNumero(est.getTrocas())
                + " | " + tempoFormatado
                + " |\n";
    }

    private static String formatarNumero(long valor) {
        return String.format("%,d", valor).replace(",", ".");
    }

    private int[] copiar(int[] original) {
        int[] copia = new int[original.length];
        System.arraycopy(original, 0, copia, 0, original.length);
        return copia;
    }


    private static void salvarRelatorio() {
        StringBuilder sb = new StringBuilder();
        sb.append("# Resultados de Benchmark — Algoritmos de Ordenação\n\n");
        sb.append("Sistema Logístico Emergencial — Estrutura de Dados\n\n");
        sb.append("Execução em ").append(LocalDateTime.now().format(DATA_FORMATTER)).append("\n\n");

        for (String secao : secoesRelatorio) {
            sb.append(secao);
        }

        try (PrintWriter writer = new PrintWriter(CAMINHO_ARQUIVO, "UTF-8")) {
            writer.print(sb);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar benchmark-results.md: " + e.getMessage(), e);
        }
    }
}