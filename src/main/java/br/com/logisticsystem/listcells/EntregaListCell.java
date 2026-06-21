package br.com.logisticsystem.listcells;

import br.com.logisticsystem.controllers.ClipboardUtil;
import br.com.logisticsystem.models.Entrega;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.time.format.DateTimeFormatter;

public class EntregaListCell extends ListCell<Entrega> {

    private static final DateTimeFormatter DATA_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM HH:mm");

    private final GridPane grid;
    private final HBox pedidoBox;
    private final Label pedidoLabel;
    private final Label destinoLabel;
    private final Label prioridadeLabel;
    private final Label statusLabel;
    private final Label distanciaLabel;
    private final Label criadoLabel;

    public EntregaListCell() {
        grid = new GridPane();
        grid.setHgap(8);
        grid.getStyleClass().add("table-row");

        ColumnConstraints c1 = new ColumnConstraints(); c1.setPercentWidth(20);
        ColumnConstraints c2 = new ColumnConstraints(); c2.setPercentWidth(18);
        ColumnConstraints c3 = new ColumnConstraints(); c3.setPercentWidth(20);
        ColumnConstraints c4 = new ColumnConstraints(); c4.setPercentWidth(16);
        ColumnConstraints c5 = new ColumnConstraints(); c5.setPercentWidth(14);
        ColumnConstraints c6 = new ColumnConstraints(); c6.setPercentWidth(12);
        grid.getColumnConstraints().addAll(c1, c2, c3, c4, c5, c6);

        pedidoLabel    = new Label(); pedidoLabel.getStyleClass().add("td-nome");

        pedidoBox = new HBox(4);
        pedidoBox.setAlignment(Pos.CENTER_LEFT);
        pedidoBox.getChildren().add(pedidoLabel);

        destinoLabel   = new Label(); destinoLabel.getStyleClass().add("td");
        prioridadeLabel = new Label(); prioridadeLabel.getStyleClass().add("td-prioridade");
        statusLabel    = new Label(); statusLabel.getStyleClass().add("td");
        distanciaLabel = new Label(); distanciaLabel.getStyleClass().add("td");
        criadoLabel    = new Label(); criadoLabel.getStyleClass().add("td");

        grid.add(pedidoBox,       0, 0);
        grid.add(destinoLabel,    1, 0);
        grid.add(prioridadeLabel, 2, 0);
        grid.add(statusLabel,     3, 0);
        grid.add(distanciaLabel,  4, 0);
        grid.add(criadoLabel,     5, 0);
    }

    @Override
    protected void updateItem(Entrega entrega, boolean vazio) {
        super.updateItem(entrega, vazio);

        if (vazio || entrega == null) {
            setGraphic(null);
            return;
        }

        pedidoLabel.setText(entrega.getPedidoVinculado().getNomeCliente());
        destinoLabel.setText(entrega.getRota().getDestino());
        prioridadeLabel.setText(entrega.getPrioridade().name().replace("_", " "));
        statusLabel.setText(entrega.getStatus().name().replace("_", " "));
        distanciaLabel.setText(entrega.getRota().getDistanciaEstimadaMt() + " km");
        criadoLabel.setText(entrega.getHorarioCriacao().format(DATA_FORMATTER));

        prioridadeLabel.getStyleClass().removeIf(c -> c.startsWith("prior-"));
        prioridadeLabel.getStyleClass().add("prior-" + entrega.getPrioridade().name().toLowerCase());

        pedidoBox.getChildren().setAll(pedidoLabel, ClipboardUtil.criarBotaoCopiar(entrega.getId()));

        setGraphic(grid);
    }
}