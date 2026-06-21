package br.com.logisticsystem.services;

import br.com.logisticsystem.algorithms.efficient.MergeSort;
import br.com.logisticsystem.algorithms.efficient.QuickSort;
import br.com.logisticsystem.algorithms.quadratic.BubbleSort;
import br.com.logisticsystem.algorithms.quadratic.InsertionSort;
import br.com.logisticsystem.algorithms.quadratic.SelectionSort;
import br.com.logisticsystem.models.Relatorio;
import br.com.logisticsystem.utils.Estatisticas;
import br.com.logisticsystem.utils.GeradorDados;
import br.com.logisticsystem.utils.Timer;

public class RelatorioService {

    private static final int TAMANHO_AMOSTRA_BENCHMARK = 2000;

    private final EstatisticaService estatisticaService;
    private final HistoricoService historicoService;

    public RelatorioService(EstatisticaService estatisticaService, HistoricoService historicoService) {
        this.estatisticaService = estatisticaService;
        this.historicoService = historicoService;
    }


    public Relatorio gerarRelatorioCompleto() {
        Relatorio relatorio = new Relatorio();

        preencherBlocoAtendimentos(relatorio);
        preencherBlocoPedidos(relatorio);
        preencherBlocoEntregas(relatorio);
        preencherBlocoHistorico(relatorio);
        preencherBlocoEstatistico(relatorio);

        return relatorio;
    }


    private void preencherBlocoAtendimentos(Relatorio relatorio) {
        relatorio.setTotalAtendimentosPendentes(estatisticaService.getTotalAtendimentosPendentes());
        relatorio.setTempoMedioEsperaSegundos(estatisticaService.getTempoMedioEspera());
    }


    private void preencherBlocoPedidos(Relatorio relatorio) {
        relatorio.setTotalPedidos(estatisticaService.getTotalPedidos());
        relatorio.setPedidosAtivos(estatisticaService.getPedidosAtivos());
    }


    private void preencherBlocoEntregas(Relatorio relatorio) {
        relatorio.setTotalEntregas(estatisticaService.getTotalEntregas());
        relatorio.setEntregasConcluidas(estatisticaService.getEntregasConcluidas());
        relatorio.setEntregasCanceladas(estatisticaService.getEntregasCanceladas());
        relatorio.setTempoMedioEntregaHoras(estatisticaService.getTempoMedioEntregaHoras());
    }


    private void preencherBlocoHistorico(Relatorio relatorio) {
        relatorio.setQuantidadeMovimentacoes(estatisticaService.getQuantidadeMovimentacoes());
    }


    private void preencherBlocoEstatistico(Relatorio relatorio) {



        relatorio.setEstruturaMaisEficiente(determinarEstruturaMaisEficiente());


        Estatisticas[] resultadosBenchmark = rodarBenchmarkOrdenacao();
        Estatisticas maisRapido = encontrarMaisRapido(resultadosBenchmark);
        relatorio.setAlgoritmoMaisRapido(maisRapido.getNomeAlgoritmo());
        relatorio.setTempoMedioProcessamentoMs(calcularTempoMedioProcessamento(resultadosBenchmark));


        relatorio.setQuantidadeOperacoes(historicoService.getQuantidadeMovimentacoes());

        relatorio.setTamanhoMedioFilas(estatisticaService.getTamanhoAtualFilas());

        relatorio.setTaxaCrescimentoSistema(estatisticaService.getTaxaCrescimentoSistema());
    }


    private String determinarEstruturaMaisEficiente() {
        int filaAtendimentos = estatisticaService.getTotalAtendimentosPendentes();
        int listaPedidos     = estatisticaService.getTotalPedidos();
        int listaEntregas    = estatisticaService.getTotalEntregas();
        int listaHistorico   = estatisticaService.getQuantidadeMovimentacoes();

        String estrutura = "Fila de Atendimentos";
        int maiorVolume = filaAtendimentos;

        if (listaPedidos > maiorVolume) {
            estrutura = "Lista Simples de Pedidos";
            maiorVolume = listaPedidos;
        }
        if (listaEntregas > maiorVolume) {
            estrutura = "Lista Simples de Entregas";
            maiorVolume = listaEntregas;
        }
        if (listaHistorico > maiorVolume) {
            estrutura = "Lista Dupla de Histórico";
            maiorVolume = listaHistorico;
        }

        return estrutura;
    }


    private Estatisticas[] rodarBenchmarkOrdenacao() {
        int[] arrayBase = GeradorDados.gerarArrayInteiros(TAMANHO_AMOSTRA_BENCHMARK);
        Timer timer = new Timer();

        Estatisticas estBubble    = medirAlgoritmo("BubbleSort", copiar(arrayBase), timer,
                (arr, est) -> BubbleSort.ordenar(arr, est));
        Estatisticas estInsertion = medirAlgoritmo("InsertionSort", copiar(arrayBase), timer,
                (arr, est) -> InsertionSort.ordenar(arr, est));
        Estatisticas estSelection = medirAlgoritmo("SelectionSort", copiar(arrayBase), timer,
                (arr, est) -> SelectionSort.ordenar(arr, est));
        Estatisticas estMerge     = medirAlgoritmo("MergeSort", copiar(arrayBase), timer,
                (arr, est) -> MergeSort.ordenar(arr, est));
        Estatisticas estQuick     = medirAlgoritmo("QuickSort", copiar(arrayBase), timer,
                (arr, est) -> QuickSort.ordenar(arr, est));

        return new Estatisticas[]{estBubble, estInsertion, estSelection, estMerge, estQuick};
    }

    private Estatisticas medirAlgoritmo(String nome, int[] array, Timer timer, AlgoritmoOrdenacao algoritmo) {
        Estatisticas estatisticas = new Estatisticas(nome, array.length);

        timer.iniciar();
        algoritmo.ordenar(array, estatisticas);
        timer.finalizar();

        estatisticas.setTempo(timer.getTempoNanosegundos());
        timer.resetar();

        return estatisticas;
    }

    private Estatisticas encontrarMaisRapido(Estatisticas[] resultados) {
        Estatisticas maisRapido = resultados[0];

        for (Estatisticas atual : resultados) {
            if (atual.getTempo() < maisRapido.getTempo()) {
                maisRapido = atual;
            }
        }

        return maisRapido;
    }

    private double calcularTempoMedioProcessamento(Estatisticas[] resultados) {
        long somaNanos = 0;

        for (Estatisticas est : resultados) {
            somaNanos += est.getTempo();
        }

        double mediaNanos = (double) somaNanos / resultados.length;
        return mediaNanos / 1_000_000.0;
    }

    private int[] copiar(int[] original) {
        int[] copia = new int[original.length];
        System.arraycopy(original, 0, copia, 0, original.length);
        return copia;
    }


    @FunctionalInterface
    private interface AlgoritmoOrdenacao {
        void ordenar(int[] array, Estatisticas estatisticas);
    }
}