package br.com.logisticsystem.controllers;

import br.com.logisticsystem.models.Relatorio;
import br.com.logisticsystem.services.RelatorioService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.format.DateTimeFormatter;

public class RelatorioController {

    private static final DateTimeFormatter DATA_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    @FXML private Label atendimentosPendentesValor;
    @FXML private Label tempoMedioEsperaValor;


    @FXML private Label totalPedidosValor;
    @FXML private Label pedidosAtivosValor;


    @FXML private Label totalEntregasValor;
    @FXML private Label entregasConcluidasValor;
    @FXML private Label entregasCanceladasValor;
    @FXML private Label tempoMedioEntregaValor;


    @FXML private Label quantidadeMovimentacoesValor;


    @FXML private Label tempoMedioProcessamentoValor;
    @FXML private Label estruturaMaisEficienteValor;
    @FXML private Label algoritmoMaisRapidoValor;
    @FXML private Label quantidadeOperacoesValor;
    @FXML private Label tamanhoMedioFilasValor;
    @FXML private Label taxaCrescimentoValor;


    @FXML private Label horarioGeracaoLabel;
    @FXML private Button gerarRelatorioButton;


    private RelatorioService relatorioService;
    private NavigationManager nav;

    public void setServices(RelatorioService relatorioService, NavigationManager nav) {
        this.relatorioService = relatorioService;
        this.nav = nav;
        gerarRelatorio();
    }

    @FXML
    public void initialize() {
        horarioGeracaoLabel.setText("Aguardando geração...");
    }


    @FXML
    private void gerarRelatorio() {
        horarioGeracaoLabel.setText("Gerando relatório...");
        if (gerarRelatorioButton != null) {
            gerarRelatorioButton.setDisable(true);
        }

        Task<Relatorio> task = new Task<>() {
            @Override
            protected Relatorio call() {
                return relatorioService.gerarRelatorioCompleto();
            }
        };

        task.setOnSucceeded(evento -> {
            exibirRelatorio(task.getValue());
            if (gerarRelatorioButton != null) {
                gerarRelatorioButton.setDisable(false);
            }
        });

        task.setOnFailed(evento -> {
            horarioGeracaoLabel.setText("Erro ao gerar relatório.");
            if (gerarRelatorioButton != null) {
                gerarRelatorioButton.setDisable(false);
            }
            task.getException().printStackTrace();
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void exibirRelatorio(Relatorio relatorio) {
        atendimentosPendentesValor.setText(String.valueOf(relatorio.getTotalAtendimentosPendentes()));
        tempoMedioEsperaValor.setText(formatarSegundos(relatorio.getTempoMedioEsperaSegundos()));

        totalPedidosValor.setText(String.valueOf(relatorio.getTotalPedidos()));
        pedidosAtivosValor.setText(String.valueOf(relatorio.getPedidosAtivos()));

        totalEntregasValor.setText(String.valueOf(relatorio.getTotalEntregas()));
        entregasConcluidasValor.setText(String.valueOf(relatorio.getEntregasConcluidas()));
        entregasCanceladasValor.setText(String.valueOf(relatorio.getEntregasCanceladas()));
        tempoMedioEntregaValor.setText(String.format("%.1f h", relatorio.getTempoMedioEntregaHoras()));

        quantidadeMovimentacoesValor.setText(String.valueOf(relatorio.getQuantidadeMovimentacoes()));

        tempoMedioProcessamentoValor.setText(String.format("%.2f ms", relatorio.getTempoMedioProcessamentoMs()));
        estruturaMaisEficienteValor.setText(relatorio.getEstruturaMaisEficiente());
        algoritmoMaisRapidoValor.setText(relatorio.getAlgoritmoMaisRapido());
        quantidadeOperacoesValor.setText(String.valueOf(relatorio.getQuantidadeOperacoes()));
        tamanhoMedioFilasValor.setText(String.format("%.1f", relatorio.getTamanhoMedioFilas()));
        taxaCrescimentoValor.setText(String.format("%.2f /dia", relatorio.getTaxaCrescimentoSistema()));

        horarioGeracaoLabel.setText("Gerado em " + relatorio.getHorarioGeracao().format(DATA_FORMATTER));
    }

    private String formatarSegundos(double segundos) {
        if (segundos < 60) {
            return String.format("%.0f s", segundos);
        }
        return String.format("%.1f min", segundos / 60.0);
    }


    @FXML
    private void voltarMenu() {
        nav.irParaMenu();
    }
}