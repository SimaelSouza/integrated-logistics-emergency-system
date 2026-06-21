package br.com.logisticsystem.listcells;

import br.com.logisticsystem.controllers.ClipboardUtil;
import br.com.logisticsystem.models.Pedido;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.time.format.DateTimeFormatter;


public class PedidoListCell extends ListCell<Pedido> {

    private static final DateTimeFormatter DATA_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM HH:mm");

    private final GridPane grid;
    private final HBox clienteBox;
    private final Label clienteLabel;
    private final Label cargaLabel;
    private final Label prioridadeLabel;
    private final Label statusLabel;
    private final Label criadoLabel;

    public PedidoListCell() {
        grid = new GridPane();
        grid.setHgap(8);
        grid.getStyleClass().add("table-row");

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(26);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(30);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setPercentWidth(18);
        ColumnConstraints c4 = new ColumnConstraints();
        c4.setPercentWidth(14);
        ColumnConstraints c5 = new ColumnConstraints();
        c5.setPercentWidth(12);
        grid.getColumnConstraints().addAll(c1, c2, c3, c4, c5);

        clienteLabel = new Label();
        clienteLabel.getStyleClass().add("td-nome");

        clienteBox = new HBox(4);
        clienteBox.setAlignment(Pos.CENTER_LEFT);
        clienteBox.getChildren().add(clienteLabel);

        cargaLabel = new Label();
        cargaLabel.getStyleClass().add("td");
        cargaLabel.setWrapText(true);

        prioridadeLabel = new Label();
        prioridadeLabel.getStyleClass().add("td-prioridade");

        statusLabel = new Label();
        statusLabel.getStyleClass().add("td");

        criadoLabel = new Label();
        criadoLabel.getStyleClass().add("td");

        grid.add(clienteBox, 0, 0);
        grid.add(cargaLabel, 1, 0);
        grid.add(prioridadeLabel, 2, 0);
        grid.add(statusLabel, 3, 0);
        grid.add(criadoLabel, 4, 0);
    }

    @Override
    protected void updateItem(Pedido pedido, boolean vazio) {
        super.updateItem(pedido, vazio);

        if (vazio || pedido == null) {
            setGraphic(null);
            return;
        }

        clienteLabel.setText(pedido.getNomeCliente());
        cargaLabel.setText(pedido.getDescricaoCarga());
        prioridadeLabel.setText(pedido.getPrioridade().name().replace("_", " "));
        statusLabel.setText(pedido.getStatus().name().replace("_", " "));
        criadoLabel.setText(pedido.getHorarioCriacao().format(DATA_FORMATTER));

        prioridadeLabel.getStyleClass().removeIf(c -> c.startsWith("prior-"));
        prioridadeLabel.getStyleClass().add("prior-" + pedido.getPrioridade().name().toLowerCase());

        clienteBox.getChildren().setAll(clienteLabel, ClipboardUtil.criarBotaoCopiar(pedido.getId()));

        setGraphic(grid);
    }
}