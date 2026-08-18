package br.com.logisticsystem.controllers;

import br.com.logisticsystem.services.AtendimentoService;
import br.com.logisticsystem.services.EntregaService;
import br.com.logisticsystem.services.HistoricoService;
import br.com.logisticsystem.services.PedidoService;
import br.com.logisticsystem.services.RelatorioService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class NavigationManager {

    private static final int LARGURA = 980;
    private static final int ALTURA  = 640;
    private static final String CSS  = "/css/style.css";

    private final Stage stage;
    private final AtendimentoService atendimentoService;
    private final PedidoService      pedidoService;
    private final EntregaService     entregaService;
    private final HistoricoService   historicoService;
    private final RelatorioService   relatorioService;

    public NavigationManager(Stage stage,
                             AtendimentoService atendimentoService,
                             PedidoService pedidoService,
                             EntregaService entregaService,
                             HistoricoService historicoService,
                             RelatorioService relatorioService) {
        this.stage               = stage;
        this.atendimentoService  = atendimentoService;
        this.pedidoService       = pedidoService;
        this.entregaService      = entregaService;
        this.historicoService    = historicoService;
        this.relatorioService    = relatorioService;
    }


    public void irParaMenu() {
        FXMLLoader loader = carregar("/views/main-view.fxml");
        if (loader == null) return;

        MainController controller = loader.getController();
        controller.setNavigationManager(this);

        exibir(loader.getRoot(), "Sistema Logístico Emergencial");
    }

    public void irParaAtendimento() {
        FXMLLoader loader = carregar("/views/atendimento-view.fxml");
        if (loader == null) return;

        AtendimentoController controller = loader.getController();
        controller.setServices(atendimentoService, this);

        exibir(loader.getRoot(), "Atendimentos");
    }

    public void irParaPedidos() {
        FXMLLoader loader = carregar("/views/pedidos-view.fxml");
        if (loader == null) return;

        PedidoController controller = loader.getController();
        controller.setServices(pedidoService, this);

        exibir(loader.getRoot(), "Pedidos");
    }

    public void irParaEntregas() {
        FXMLLoader loader = carregar("/views/entregas-view.fxml");
        if (loader == null) return;

        EntregaController controller = loader.getController();
        controller.setServices(pedidoService, entregaService, this);

        exibir(loader.getRoot(), "Entregas");
    }

    public void irParaHistorico() {
        FXMLLoader loader = carregar("/views/historico-view.fxml");
        if (loader == null) return;

        HistoricoController controller = loader.getController();
        controller.setServices(historicoService, this);

        exibir(loader.getRoot(), "Histórico");
    }

    public void irParaRelatorios() {
        FXMLLoader loader = carregar("/views/relatorio-view.fxml");
        if (loader == null) return;

        RelatorioController controller = loader.getController();
        controller.setServices(relatorioService, this);

        exibir(loader.getRoot(), "Relatórios");
    }


    private FXMLLoader carregar(String caminho) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    NavigationManager.class.getResource(caminho));
            loader.load();
            return loader;
        } catch (IOException e) {
            System.err.println("Erro ao carregar FXML: " + caminho);
            e.printStackTrace();
            return null;
        }
    }

    private void exibir(Parent root, String titulo) {
        Scene cena = new Scene(root, LARGURA, ALTURA);
        cena.getStylesheets().add(
                NavigationManager.class.getResource(CSS).toExternalForm());
        stage.setScene(cena);
        stage.setTitle(titulo);
    }
}