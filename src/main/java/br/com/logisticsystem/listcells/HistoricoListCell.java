package br.com.logisticsystem.listcells;

import br.com.logisticsystem.controllers.ClipboardUtil;
import br.com.logisticsystem.models.Movimentacao;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.time.format.DateTimeFormatter;


public class HistoricoListCell extends ListCell<Movimentacao> {

    private static final DateTimeFormatter HORA_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM HH:mm:ss");

    private final HBox row;
    private final HBox tipoBox;
    private final Label tipoLabel;
    private final Label horarioLabel;

    public HistoricoListCell() {
        row = new HBox(10);
        row.getStyleClass().add("table-row");

        tipoLabel = new Label();
        tipoLabel.getStyleClass().add("td-nome");

        tipoBox = new HBox(4);
        tipoBox.setAlignment(Pos.CENTER_LEFT);
        tipoBox.getChildren().add(tipoLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        horarioLabel = new Label();
        horarioLabel.getStyleClass().add("td");

        row.getChildren().addAll(tipoBox, spacer, horarioLabel);
    }

    @Override
    protected void updateItem(Movimentacao movimentacao, boolean vazio) {
        super.updateItem(movimentacao, vazio);

        if (vazio || movimentacao == null) {
            setGraphic(null);
            return;
        }

        tipoLabel.setText(movimentacao.getTipoOperacao().name().replace("_", " "));
        horarioLabel.setText(movimentacao.getHorario().format(HORA_FORMATTER));


        tipoBox.getChildren().setAll(tipoLabel, ClipboardUtil.criarBotaoCopiar(movimentacao.getId()));

        setGraphic(row);
    }
}