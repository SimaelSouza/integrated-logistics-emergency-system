package br.com.logisticsystem.controllers;

import br.com.logisticsystem.services.AtendimentoService;
import br.com.logisticsystem.services.EntregaService;
import br.com.logisticsystem.services.PedidoService;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MainController {

    private static final DateTimeFormatter HORA_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    @FXML private Label clockLabel;
    @FXML private Label statusLabel;

    private Timeline relogio;
    private NavigationManager nav;


    public void setNavigationManager(NavigationManager nav) {
        this.nav = nav;
    }


    @FXML
    public void initialize() {
        iniciarRelogio();
    }

    private void iniciarRelogio() {
        atualizarHora();
        relogio = new Timeline(new KeyFrame(Duration.seconds(1), e -> atualizarHora()));
        relogio.setCycleCount(Animation.INDEFINITE);
        relogio.play();
    }

    private void atualizarHora() {
        clockLabel.setText(LocalTime.now().format(HORA_FORMATTER));
    }


    @FXML private void abrirAtendimento() { nav.irParaAtendimento(); }
    @FXML private void abrirPedidos()     { nav.irParaPedidos(); }
    @FXML private void abrirEntregas()    { nav.irParaEntregas(); }
    @FXML private void abrirHistorico()   { nav.irParaHistorico(); }
    @FXML private void abrirRelatorios()  { nav.irParaRelatorios(); }

    public void pararRelogio() {
        if (relogio != null) relogio.stop();
    }
}