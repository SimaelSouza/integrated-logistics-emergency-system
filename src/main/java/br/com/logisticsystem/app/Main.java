package br.com.logisticsystem.app;

import br.com.logisticsystem.controllers.MainController;
import br.com.logisticsystem.controllers.NavigationManager;
import br.com.logisticsystem.services.AtendimentoService;
import br.com.logisticsystem.services.EntregaService;
import br.com.logisticsystem.services.EstatisticaService;
import br.com.logisticsystem.services.HistoricoService;
import br.com.logisticsystem.services.PedidoService;
import br.com.logisticsystem.services.RelatorioService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        HistoricoService   historicoService   = new HistoricoService();
        AtendimentoService atendimentoService = new AtendimentoService(historicoService);
        PedidoService      pedidoService      = new PedidoService(historicoService);
        EntregaService     entregaService     = new EntregaService(historicoService);

        EstatisticaService estatisticaService = new EstatisticaService(
                atendimentoService, pedidoService, entregaService, historicoService);
        RelatorioService   relatorioService   = new RelatorioService(estatisticaService, historicoService);

        NavigationManager nav = new NavigationManager(
                stage, atendimentoService, pedidoService, entregaService,
                historicoService, relatorioService);

        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource("/views/main-view.fxml"));
        Parent root = loader.load();

        MainController controller = loader.getController();
        controller.setNavigationManager(nav);

        Scene scene = new Scene(root, 980, 640);
        scene.getStylesheets().add(
                Main.class.getResource("/css/style.css").toExternalForm());

        stage.setTitle("Sistema Logístico Emergencial");
        stage.setScene(scene);
        stage.setMinWidth(980);
        stage.setMinHeight(640);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}