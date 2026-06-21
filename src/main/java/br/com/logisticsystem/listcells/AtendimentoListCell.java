package br.com.logisticsystem.listcells;

import br.com.logisticsystem.controllers.ClipboardUtil;
import br.com.logisticsystem.models.Atendimento;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.time.format.DateTimeFormatter;


public class AtendimentoListCell extends ListCell<Atendimento> {

    private static final DateTimeFormatter HORA_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    private final GridPane grid;
    private final HBox nomeBox;
    private final Label nomeLabel;
    private final Label prioridadeLabel;
    private final Label tipoLabel;
    private final Label horarioLabel;

    public AtendimentoListCell() {
        grid = new GridPane();
        grid.setHgap(8);
        grid.getStyleClass().add("table-row");

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(32);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(22);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setPercentWidth(24);
        ColumnConstraints c4 = new ColumnConstraints();
        c4.setPercentWidth(22);
        grid.getColumnConstraints().addAll(c1, c2, c3, c4);

        nomeLabel = new Label();
        nomeLabel.getStyleClass().add("td-nome");

        nomeBox = new HBox(4);
        nomeBox.setAlignment(Pos.CENTER_LEFT);
        nomeBox.getChildren().add(nomeLabel);

        prioridadeLabel = new Label();
        prioridadeLabel.getStyleClass().add("td-prioridade");

        tipoLabel = new Label();
        tipoLabel.getStyleClass().add("td");

        horarioLabel = new Label();
        horarioLabel.getStyleClass().add("td");

        grid.add(nomeBox, 0, 0);
        grid.add(prioridadeLabel, 1, 0);
        grid.add(tipoLabel, 2, 0);
        grid.add(horarioLabel, 3, 0);
    }

    @Override
    protected void updateItem(Atendimento atendimento, boolean vazio) {
        super.updateItem(atendimento, vazio);

        if (vazio || atendimento == null) {
            setGraphic(null);
            return;
        }

        nomeLabel.setText(atendimento.getNomeSolicitante());
        prioridadeLabel.setText(atendimento.getPrioridade().name().replace("_", " "));
        tipoLabel.setText(atendimento.getTipoOcorrencia().name().replace("_", " "));
        horarioLabel.setText(atendimento.getHorarioSolicitacao().format(HORA_FORMATTER));

        prioridadeLabel.getStyleClass().removeIf(c -> c.startsWith("prior-"));
        prioridadeLabel.getStyleClass().add("prior-" + atendimento.getPrioridade().name().toLowerCase());

        nomeBox.getChildren().setAll(nomeLabel, ClipboardUtil.criarBotaoCopiar(atendimento.getId()));

        setGraphic(grid);
    }
}