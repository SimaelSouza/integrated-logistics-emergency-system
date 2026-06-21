package br.com.logisticsystem.controllers;

import br.com.logisticsystem.listcells.PedidoListCell;
import br.com.logisticsystem.models.EnumPrioridade;
import br.com.logisticsystem.models.EnumStatus;
import br.com.logisticsystem.models.Pedido;
import br.com.logisticsystem.services.PedidoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class PedidoController {

    private static final DateTimeFormatter DATA_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    @FXML private TextField nomeClienteField;
    @FXML private TextArea descricaoCargaField;
    @FXML private ComboBox<EnumPrioridade> prioridadeCadastroCombo;

    @FXML private TextField idRemoverField;


    @FXML private Label totalPedidosLabel;
    @FXML private Button desfazerButton;
    @FXML private Button refazerButton;
    @FXML private ToggleGroup filtroGroup;
    @FXML private RadioButton radioTodos;
    @FXML private RadioButton radioAtivos;
    @FXML private RadioButton radioPrioridade;
    @FXML private RadioButton radioHorario;
    @FXML private ComboBox<EnumStatus> statusBuscaCombo;
    @FXML private ListView<Pedido> pedidosListView;


    @FXML private TextField idBuscarField;
    @FXML private VBox resultadoBuscaBox;
    @FXML private Label resultadoNomeLabel;
    @FXML private Label resultadoCargaLabel;
    @FXML private Label resultadoPrioridadeBadge;
    @FXML private Label resultadoStatusBadge;
    @FXML private Label resultadoHorarioLabel;
    @FXML private HBox resultadoIdBox;

    @FXML private TextField idAtualizarField;
    @FXML private TextArea novaDescricaoField;
    @FXML private ComboBox<EnumPrioridade> novaPrioridadeCombo;

    @FXML private TextField idStatusField;
    @FXML private ComboBox<EnumStatus> novoStatusCombo;


    private PedidoService pedidoService;
    private NavigationManager nav;
    private final ObservableList<Pedido> pedidosObservaveis = FXCollections.observableArrayList();

    public void setServices(PedidoService pedidoService, NavigationManager nav) {
        this.pedidoService = pedidoService;
        this.nav = nav;
        atualizarListagem();
    }

    @FXML
    public void initialize() {
        prioridadeCadastroCombo.setItems(FXCollections.observableArrayList(EnumPrioridade.values()));
        novaPrioridadeCombo.setItems(FXCollections.observableArrayList(EnumPrioridade.values()));
        statusBuscaCombo.setItems(FXCollections.observableArrayList(EnumStatus.values()));
        novoStatusCombo.setItems(FXCollections.observableArrayList(EnumStatus.values()));

        pedidosListView.setItems(pedidosObservaveis);
        pedidosListView.setCellFactory(listView -> new PedidoListCell());
    }


    @FXML
    private void cadastrarPedido() {
        try {
            String nome = nomeClienteField.getText();
            String descricao = descricaoCargaField.getText();
            EnumPrioridade prioridade = prioridadeCadastroCombo.getValue();

            if (prioridade == null) {
                exibirAlerta(Alert.AlertType.WARNING, "Campo obrigatório", "Selecione a prioridade.");
                return;
            }

            pedidoService.cadastrar(nome, descricao, prioridade);

            limparCamposCadastro();
            atualizarListagem();

        } catch (IllegalArgumentException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro ao cadastrar", e.getMessage());
        }
    }

    private void limparCamposCadastro() {
        nomeClienteField.clear();
        descricaoCargaField.clear();
        prioridadeCadastroCombo.setValue(null);
    }


    @FXML
    private void removerPedido() {
        UUID id = lerUuid(idRemoverField.getText());

        if (id == null) {
            exibirAlerta(Alert.AlertType.WARNING, "ID inválido", "Informe um ID de pedido válido.");
            return;
        }

        boolean removido = pedidoService.remover(id);

        if (!removido) {
            exibirAlerta(Alert.AlertType.WARNING, "Não encontrado", "Nenhum pedido com esse ID.");
            return;
        }

        idRemoverField.clear();
        atualizarListagem();
    }


    @FXML
    private void buscarPorId() {
        UUID id = lerUuid(idBuscarField.getText());

        if (id == null) {
            exibirAlerta(Alert.AlertType.WARNING, "ID inválido", "Informe um ID de pedido válido.");
            return;
        }

        Pedido pedido = pedidoService.buscarPorId(id);

        if (pedido == null) {
            limparResultadoBusca();
            exibirAlerta(Alert.AlertType.INFORMATION, "Não encontrado", "Nenhum pedido com esse ID.");
            return;
        }

        resultadoNomeLabel.setText(pedido.getNomeCliente());
        resultadoCargaLabel.setText(pedido.getDescricaoCarga());
        resultadoPrioridadeBadge.setText(pedido.getPrioridade().name().replace("_", " "));
        setBadgeClasse(resultadoPrioridadeBadge, badgePorPrioridade(pedido.getPrioridade()));
        resultadoStatusBadge.setText(pedido.getStatus().name().replace("_", " "));
        setBadgeClasse(resultadoStatusBadge, "badge-gray");
        resultadoHorarioLabel.setText("Criado em " + pedido.getHorarioCriacao().format(DATA_FORMATTER));

        exibirIdComBotaoCopiar(pedido.getId());
    }

    private void exibirIdComBotaoCopiar(UUID id) {
        resultadoIdBox.getChildren().clear();

        Label idLabel = new Label("ID: " + id);
        idLabel.getStyleClass().add("next-horario");

        resultadoIdBox.getChildren().add(idLabel);
        resultadoIdBox.getChildren().add(ClipboardUtil.criarBotaoCopiar(id));
    }

    private void limparResultadoBusca() {
        resultadoNomeLabel.setText("—");
        resultadoCargaLabel.setText("");
        resultadoPrioridadeBadge.setText("");
        resultadoStatusBadge.setText("");
        resultadoHorarioLabel.setText("");
        resultadoIdBox.getChildren().clear();
    }


    @FXML
    private void atualizarPedido() {
        UUID id = lerUuid(idAtualizarField.getText());

        if (id == null) {
            exibirAlerta(Alert.AlertType.WARNING, "ID inválido", "Informe um ID de pedido válido.");
            return;
        }

        String novaDescricao = novaDescricaoField.getText();
        EnumPrioridade novaPrioridade = novaPrioridadeCombo.getValue();

        try {
            boolean atualizado = pedidoService.atualizar(id, novaDescricao, novaPrioridade);

            if (!atualizado) {
                exibirAlerta(Alert.AlertType.WARNING, "Não encontrado", "Nenhum pedido com esse ID.");
                return;
            }

            idAtualizarField.clear();
            novaDescricaoField.clear();
            novaPrioridadeCombo.setValue(null);
            atualizarListagem();

        } catch (IllegalArgumentException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro ao atualizar", e.getMessage());
        }
    }


    @FXML
    private void alterarStatus() {
        UUID id = lerUuid(idStatusField.getText());
        EnumStatus novoStatus = novoStatusCombo.getValue();

        if (id == null) {
            exibirAlerta(Alert.AlertType.WARNING, "ID inválido", "Informe um ID de pedido válido.");
            return;
        }
        if (novoStatus == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Campo obrigatório", "Selecione o novo status.");
            return;
        }

        boolean alterado = pedidoService.alterarStatus(id, novoStatus);

        if (!alterado) {
            exibirAlerta(Alert.AlertType.WARNING, "Não encontrado", "Nenhum pedido com esse ID.");
            return;
        }

        idStatusField.clear();
        novoStatusCombo.setValue(null);
        atualizarListagem();
    }


    @FXML
    private void buscarPorStatus() {
        EnumStatus status = statusBuscaCombo.getValue();

        if (status == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Campo obrigatório", "Selecione um status para buscar.");
            return;
        }

        Pedido[] resultado = pedidoService.listarPorStatus(status);
        popularLista(resultado);
        desmarcarRadiosFiltro();
    }


    @FXML
    private void atualizarListagem() {
        totalPedidosLabel.setText(pedidoService.getTotalPedidos() + " PEDIDOS");
        aplicarFiltro();
        atualizarEstadoBotoesUndoRedo();
    }

    private void atualizarEstadoBotoesUndoRedo() {
        desfazerButton.setDisable(!pedidoService.podeDesfazer());
        refazerButton.setDisable(!pedidoService.podeRefazer());
    }

    @FXML
    private void desfazer() {
        var operacao = pedidoService.desfazer();
        if (operacao == null) {
            exibirAlerta(Alert.AlertType.INFORMATION, "Nada a desfazer",
                    "Não há operações de pedidos para desfazer.");
            return;
        }
        atualizarListagem();
    }

    @FXML
    private void refazer() {
        var operacao = pedidoService.refazer();
        if (operacao == null) {
            exibirAlerta(Alert.AlertType.INFORMATION, "Nada a refazer",
                    "Não há operações de pedidos para refazer.");
            return;
        }
        atualizarListagem();
    }

    @FXML
    private void aplicarFiltro() {
        Pedido[] resultado;

        if (radioAtivos.isSelected()) {
            resultado = pedidoService.listarPedidosAtivos();
        } else if (radioPrioridade.isSelected()) {
            resultado = pedidoService.listarOrdenadosPorPrioridade();
        } else if (radioHorario.isSelected()) {
            resultado = pedidoService.listarOrdenadosPorHorario();
        } else {
            resultado = pedidoService.listarPedidos();
        }

        popularLista(resultado);
    }

    private void popularLista(Pedido[] pedidos) {
        pedidosObservaveis.clear();
        if (pedidos == null) {
            return;
        }
        for (Pedido p : pedidos) {
            if (p != null) {
                pedidosObservaveis.add(p);
            }
        }
    }

    private void desmarcarRadiosFiltro() {
        filtroGroup.selectToggle(null);
    }


    private UUID lerUuid(String texto) {
        if (texto == null || texto.isBlank()) {
            return null;
        }
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

    private String badgePorPrioridade(EnumPrioridade prioridade) {
        switch (prioridade) {
            case CRITICA: return "badge-red";
            case ALTA: return "badge-coral";
            case MEDIA: return "badge-amber";
            case BAIXA: return "badge-teal";
            default: return "badge-gray";
        }
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