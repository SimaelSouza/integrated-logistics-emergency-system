package br.com.logisticsystem.controllers;

import br.com.logisticsystem.algorithms.efficient.QuickSort;
import br.com.logisticsystem.listcells.AtendimentoListCell;
import br.com.logisticsystem.models.Atendimento;
import br.com.logisticsystem.models.EnumPrioridade;
import br.com.logisticsystem.models.EnumTipo;
import br.com.logisticsystem.services.AtendimentoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.UUID;

public class AtendimentoController {

    private static final DateTimeFormatter HORA_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss");


    @FXML private TextField nomeField;
    @FXML private ComboBox<EnumPrioridade> prioridadeCadastroCombo;
    @FXML private ComboBox<EnumTipo> tipoOcorrenciaCombo;


    @FXML private TextField idAlterarField;
    @FXML private ComboBox<EnumPrioridade> novaPrioridadeCombo;


    @FXML private TextField idRemoverField;


    @FXML private Label proximoNomeLabel;
    @FXML private Label proximoTipoLabel;
    @FXML private Label proximoPrioridadeBadge;
    @FXML private Label proximoOrigemLabel;
    @FXML private Label proximoHorarioLabel;
    @FXML private HBox proximoIdBox;
    @FXML private Label tempoMedioLabel;


    @FXML private Label totalPendentesLabel;
    @FXML private Button desfazerButton;
    @FXML private Button refazerButton;
    @FXML private ToggleGroup filtroGroup;
    @FXML private RadioButton radioPrioridade;
    @FXML private RadioButton radioHorario;
    @FXML private ListView<Atendimento> atendimentosListView;


    private AtendimentoService atendimentoService;
    private NavigationManager nav;
    private final ObservableList<Atendimento> atendimentosObservaveis = FXCollections.observableArrayList();

    public void setServices(AtendimentoService atendimentoService, NavigationManager nav) {
        this.atendimentoService = atendimentoService;
        this.nav = nav;
        atualizarListagem();
        atualizarConsultaProximo();
    }

    @FXML
    public void initialize() {
        prioridadeCadastroCombo.setItems(FXCollections.observableArrayList(EnumPrioridade.values()));
        novaPrioridadeCombo.setItems(FXCollections.observableArrayList(EnumPrioridade.values()));
        tipoOcorrenciaCombo.setItems(FXCollections.observableArrayList(EnumTipo.values()));

        atendimentosListView.setItems(atendimentosObservaveis);
        atendimentosListView.setCellFactory(listView -> new AtendimentoListCell());
    }


    @FXML
    private void cadastrarAtendimento() {
        try {
            String nome = nomeField.getText();
            EnumPrioridade prioridade = prioridadeCadastroCombo.getValue();
            EnumTipo tipo = tipoOcorrenciaCombo.getValue();

            if (prioridade == null || tipo == null) {
                exibirAlerta(Alert.AlertType.WARNING, "Campos obrigatórios",
                        "Selecione a prioridade e o tipo de ocorrência.");
                return;
            }

            atendimentoService.cadastrar(nome, prioridade, tipo);

            limparCamposCadastro();
            atualizarListagem();
            atualizarConsultaProximo();

        } catch (IllegalArgumentException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro ao cadastrar", e.getMessage());
        }
    }

    private void limparCamposCadastro() {
        nomeField.clear();
        prioridadeCadastroCombo.setValue(null);
        tipoOcorrenciaCombo.setValue(null);
    }


    @FXML
    private void atualizarPrioridade() {
        try {
            Atendimento atendimento = buscarPorIdTexto(idAlterarField.getText());
            EnumPrioridade novaPrioridade = novaPrioridadeCombo.getValue();

            if (atendimento == null) {
                exibirAlerta(Alert.AlertType.WARNING, "Não encontrado",
                        "Nenhum atendimento pendente com esse ID.");
                return;
            }
            if (novaPrioridade == null) {
                exibirAlerta(Alert.AlertType.WARNING, "Campo obrigatório",
                        "Selecione a nova prioridade.");
                return;
            }

            boolean alterado = atendimentoService.alterarPrioridade(atendimento, novaPrioridade);

            if (!alterado) {
                exibirAlerta(Alert.AlertType.WARNING, "Não foi possível atualizar",
                        "O atendimento não está mais pendente.");
                return;
            }

            idAlterarField.clear();
            novaPrioridadeCombo.setValue(null);
            atualizarListagem();
            atualizarConsultaProximo();

        } catch (IllegalArgumentException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro ao atualizar", e.getMessage());
        }
    }


    @FXML
    private void removerAtendimento() {
        Atendimento atendimento = buscarPorIdTexto(idRemoverField.getText());

        if (atendimento == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Não encontrado",
                    "Nenhum atendimento pendente com esse ID.");
            return;
        }

        boolean removido = atendimentoService.removerDaFila(atendimento);

        if (!removido) {
            exibirAlerta(Alert.AlertType.WARNING, "Não foi possível remover",
                    "O atendimento não está mais pendente.");
            return;
        }

        idRemoverField.clear();
        atualizarListagem();
        atualizarConsultaProximo();
    }


    @FXML
    private void consultarProximo() {
        atualizarConsultaProximo();
    }

    @FXML
    private void atenderProximo() {
        Atendimento atendido = atendimentoService.atenderProximo();

        if (atendido == null) {
            exibirAlerta(Alert.AlertType.INFORMATION, "Fila vazia",
                    "Não há atendimentos pendentes.");
        }

        atualizarConsultaProximo();
        atualizarListagem();
        atualizarTempoMedio();
    }

    private void atualizarConsultaProximo() {
        Atendimento proximo = atendimentoService.consultarProximo();

        if (proximo == null) {
            proximoNomeLabel.setText("—");
            proximoTipoLabel.setText("Nenhum atendimento na fila");
            proximoPrioridadeBadge.setText("SEM PRIORIDADE");
            setBadgeClasse("badge-gray");
            proximoOrigemLabel.setText("");
            proximoHorarioLabel.setText("");
            proximoIdBox.getChildren().clear();
            return;
        }

        proximoNomeLabel.setText(proximo.getNomeSolicitante());
        proximoTipoLabel.setText(proximo.getTipoOcorrencia().name().replace("_", " "));
        proximoPrioridadeBadge.setText(proximo.getPrioridade().name().replace("_", " "));
        setBadgeClasse(badgePorPrioridade(proximo.getPrioridade()));
        proximoOrigemLabel.setText(
                proximo.getPrioridade() == EnumPrioridade.SEM_PRIORIDADE ? "Fila comum" : "Fila prioritária"
        );
        proximoHorarioLabel.setText("Solicitado às " + proximo.getHorarioSolicitacao().format(HORA_FORMATTER));

        exibirIdComBotaoCopiar(proximo.getId());
    }

    private void exibirIdComBotaoCopiar(java.util.UUID id) {
        proximoIdBox.getChildren().clear();

        Label idLabel = new Label("ID: " + id);
        idLabel.getStyleClass().add("next-horario");

        proximoIdBox.getChildren().add(idLabel);
        proximoIdBox.getChildren().add(ClipboardUtil.criarBotaoCopiar(id));
    }

    private void setBadgeClasse(String classe) {
        proximoPrioridadeBadge.getStyleClass().removeIf(c -> c.startsWith("badge-"));
        proximoPrioridadeBadge.getStyleClass().add(classe);
    }

    private String badgePorPrioridade(EnumPrioridade prioridade) {
        switch (prioridade) {
            case CRITICA: return "badge-red";
            case ALTA: return "badge-coral";
            case MEDIA: return "badge-amber";
            case BAIXA: return "badge-teal";
            default: return "badge-gray";
        }
    }

    private void atualizarTempoMedio() {
        double mediaSegundos = atendimentoService.calcularTempoMedioEsperaSegundos();

        if (atendimentoService.getTotalAtendimentos() == 0) {
            tempoMedioLabel.setText("Tempo médio de espera: —");
            return;
        }

        tempoMedioLabel.setText(String.format("Tempo médio de espera: %.0f s", mediaSegundos));
    }

    @FXML
    private void atualizarListagem() {
        Atendimento[] comum = atendimentoService.listarFilaComum();
        Atendimento[] prioritaria = atendimentoService.listarFilaPrioritaria();

        atendimentosObservaveis.clear();

        for (Atendimento a : prioritaria) {
            if (a != null) {
                atendimentosObservaveis.add(a);
            }
        }
        for (Atendimento a : comum) {
            if (a != null) {
                atendimentosObservaveis.add(a);
            }
        }

        aplicarFiltro();
        totalPendentesLabel.setText(atendimentoService.getTotalPendentes() + " PENDENTES");
        atualizarEstadoBotoesUndoRedo();
    }

    private void atualizarEstadoBotoesUndoRedo() {
        desfazerButton.setDisable(!atendimentoService.podeDesfazer());
        refazerButton.setDisable(!atendimentoService.podeRefazer());
    }

    @FXML
    private void desfazer() {
        var operacao = atendimentoService.desfazer();
        if (operacao == null) {
            exibirAlerta(Alert.AlertType.INFORMATION, "Nada a desfazer",
                    "Não há operações de atendimentos para desfazer.");
            return;
        }
        atualizarListagem();
        atualizarConsultaProximo();
    }

    @FXML
    private void refazer() {
        var operacao = atendimentoService.refazer();
        if (operacao == null) {
            exibirAlerta(Alert.AlertType.INFORMATION, "Nada a refazer",
                    "Não há operações de atendimentos para refazer.");
            return;
        }
        atualizarListagem();
        atualizarConsultaProximo();
    }

    @FXML
    private void aplicarFiltro() {
        if (radioPrioridade.isSelected()) {
            ordenarPorPrioridade();
        } else {
            ordenarPorHorario();
        }
    }


    private void ordenarPorPrioridade() {
        Atendimento[] atendimentos = atendimentosObservaveis.toArray(new Atendimento[0]);
        QuickSort.sort(
                atendimentos,
                Comparator.comparingInt(
                        a -> a.getPrioridade().getNivel()
                )
        );

    }

    private void ordenarPorHorario() {
        Atendimento[] atendimentos = atendimentosObservaveis.toArray(new Atendimento[0]);
        QuickSort.sort(
                atendimentos,
                Comparator.comparing(
                        Atendimento::getHorarioSolicitacao
                )
        );
    }

    private Atendimento buscarPorIdTexto(String idTexto) {
        if (idTexto == null || idTexto.isBlank()) {
            return null;
        }

        UUID id;
        try {
            id = UUID.fromString(idTexto.trim());
        } catch (IllegalArgumentException e) {
            return null;
        }

        for (Atendimento a : atendimentosObservaveis) {
            if (a.getId().equals(id)) {
                return a;
            }
        }
        return null;
    }

    private void exibirAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    @FXML
    private void voltarMenu() {
        nav.irParaMenu();
    }
}