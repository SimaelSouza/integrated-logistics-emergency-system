package br.com.logisticsystem.controllers;

import br.com.logisticsystem.listcells.HistoricoListCell;
import br.com.logisticsystem.models.Movimentacao;
import br.com.logisticsystem.services.HistoricoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class HistoricoController {

    private static final DateTimeFormatter DATA_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    @FXML private Label tipoOperacaoLabel;
    @FXML private Label posicaoLabel;
    @FXML private Label descricaoLabel;
    @FXML private Label entidadeLabel;
    @FXML private Label horarioLabel;
    @FXML private HBox idBox;

    @FXML private TextField idBuscarField;


    @FXML private Label totalMovimentacoesLabel;
    @FXML private ToggleGroup filtroGroup;
    @FXML private RadioButton radioTipoOperacao;
    @FXML private RadioButton radioHorario;
    @FXML private ListView<Movimentacao> movimentacoesListView;


    private HistoricoService historicoService;
    private NavigationManager nav;

    private final ObservableList<Movimentacao> movimentacoesObservaveis = FXCollections.observableArrayList();

    public void setServices(HistoricoService historicoService, NavigationManager nav) {
        this.historicoService = historicoService;
        this.nav = nav;
        atualizarListagem();
        exibirMovimentacaoAtual(historicoService.estaVazio() ? null : historicoService.primeiraMovimentacao());
    }

    @FXML
    public void initialize() {
        movimentacoesListView.setItems(movimentacoesObservaveis);
        movimentacoesListView.setCellFactory(lv -> new HistoricoListCell());

        movimentacoesListView.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                exibirMovimentacaoAtual(novo);
            }
        });
    }


    @FXML
    private void irParaPrimeira() {
        exibirMovimentacaoAtual(historicoService.primeiraMovimentacao());
    }

    @FXML
    private void irParaUltima() {
        exibirMovimentacaoAtual(historicoService.ultimaMovimentacao());
    }

    @FXML
    private void irParaAnterior() {
        Movimentacao anterior = historicoService.movimentacaoAnterior();
        if (anterior == null) {
            exibirAlerta("Início do histórico", "Não há movimentações anteriores.");
            return;
        }
        exibirMovimentacaoAtual(anterior);
    }

    @FXML
    private void irParaProxima() {
        Movimentacao proxima = historicoService.proximaMovimentacao();
        if (proxima == null) {
            exibirAlerta("Fim do histórico", "Não há movimentações posteriores.");
            return;
        }
        exibirMovimentacaoAtual(proxima);
    }

    private void exibirMovimentacaoAtual(Movimentacao movimentacao) {
        if (movimentacao == null) {
            tipoOperacaoLabel.setText("—");
            setBadgeClasse(tipoOperacaoLabel, "badge-gray");
            descricaoLabel.setText("Nenhuma movimentação registrada");
            entidadeLabel.setText("—");
            horarioLabel.setText("");
            idBox.getChildren().clear();
            posicaoLabel.setText("");
            return;
        }

        tipoOperacaoLabel.setText(movimentacao.getTipoOperacao().name().replace("_", " "));
        setBadgeClasse(tipoOperacaoLabel, "badge-amber");
        descricaoLabel.setText(movimentacao.getDescricao());

        Object entidade = movimentacao.getEntidadeEnvolvida();
        entidadeLabel.setText(entidade != null ? entidade.toString() : "Nenhuma entidade associada");

        horarioLabel.setText(movimentacao.getHorario().format(DATA_FORMATTER));

        Label idLabel = new Label("ID: " + movimentacao.getId());
        idLabel.getStyleClass().add("next-horario");
        idBox.getChildren().setAll(idLabel, ClipboardUtil.criarBotaoCopiar(movimentacao.getId()));

        int total = historicoService.getQuantidadeMovimentacoes();
        int posicao = encontrarPosicao(movimentacao);
        posicaoLabel.setText(posicao >= 0 ? (posicao + 1) + " de " + total : "");
    }

    private int encontrarPosicao(Movimentacao alvo) {
        Movimentacao[] todas = historicoService.listarHistorico();
        for (int i = 0; i < todas.length; i++) {
            if (todas[i].getId().equals(alvo.getId())) {
                return i;
            }
        }
        return -1;
    }


    @FXML
    private void buscarPorId() {
        UUID id = lerUuid(idBuscarField.getText());

        if (id == null) {
            exibirAlerta("ID inválido", "Informe um ID de movimentação válido.");
            return;
        }

        Movimentacao encontrada = historicoService.buscarPorId(id);

        if (encontrada == null) {
            exibirAlerta("Não encontrado", "Nenhuma movimentação com esse ID.");
            return;
        }

        exibirMovimentacaoAtual(encontrada);
    }


    @FXML
    private void atualizarListagem() {
        totalMovimentacoesLabel.setText(historicoService.getQuantidadeMovimentacoes() + " REGISTROS");
        aplicarFiltro();
    }

    @FXML
    private void aplicarFiltro() {
        Movimentacao[] resultado;

        if (radioHorario.isSelected()) {
            resultado = historicoService.listarOrdenadoPorHorario();
        } else {
            resultado = historicoService.listarOrdenadoPorTipoOperacao();
        }

        movimentacoesObservaveis.clear();
        if (resultado != null) {
            for (Movimentacao m : resultado) {
                if (m != null) movimentacoesObservaveis.add(m);
            }
        }
    }


    private UUID lerUuid(String texto) {
        if (texto == null || texto.isBlank()) return null;
        try {
            return UUID.fromString(texto.trim());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private void setBadgeClasse(Label badge, String classe) {
        badge.getStyleClass().removeIf(c -> c.startsWith("badge-"));
        badge.getStyleClass().add(classe);
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
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