package br.com.logisticsystem.controllers;

import br.com.logisticsystem.listcells.EntregaListCell;
import br.com.logisticsystem.models.Entrega;
import br.com.logisticsystem.models.EnumPrioridade;
import br.com.logisticsystem.models.EnumStatus;
import br.com.logisticsystem.models.Pedido;
import br.com.logisticsystem.services.EntregaService;
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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class EntregaController {

    private static final DateTimeFormatter DATA_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    @FXML private ComboBox<Pedido> pedidoCombo;
    @FXML private ComboBox<EnumPrioridade> prioridadeCriarCombo;
    @FXML private ComboBox<EnumStatus> statusCriarCombo;
    @FXML private TextField destinoField;
    @FXML private TextField observacoesField;

    @FXML private TextField idAlterarField;
    @FXML private TextField novoDestinoField;
    @FXML private TextField novasObservacoesField;
    @FXML private ComboBox<EnumPrioridade> novaPrioridadeCombo;


    @FXML private Label totalEntregasLabel;
    @FXML private Button desfazerButton;
    @FXML private Button refazerButton;
    @FXML private ToggleGroup filtroGroup;
    @FXML private RadioButton radioTodas;
    @FXML private RadioButton radioAtivas;
    @FXML private RadioButton radioPrioridade;
    @FXML private RadioButton radioHorario;
    @FXML private ComboBox<EnumStatus> statusBuscaCombo;
    @FXML private ListView<Entrega> entregasListView;


    @FXML private TextField idStatusField;
    @FXML private ComboBox<EnumStatus> novoStatusCombo;


    @FXML private TextField idBuscarField;
    @FXML private Label resultadoPedidoLabel;
    @FXML private Label resultadoDestinoLabel;
    @FXML private Label resultadoPrioridadeBadge;
    @FXML private Label resultadoStatusBadge;
    @FXML private Label resultadoDistanciaLabel;
    @FXML private Label resultadoHorarioLabel;
    @FXML private HBox resultadoIdBox;


    private EntregaService entregaService;
    private PedidoService pedidoService;
    private NavigationManager nav;

    private final ObservableList<Entrega> entregasObservaveis = FXCollections.observableArrayList();
    private Entrega entregaSelecionada = null;

    public void setServices(PedidoService pedidoService, EntregaService entregaService, NavigationManager nav) {
        this.pedidoService  = pedidoService;
        this.entregaService = entregaService;
        this.nav = nav;
        popularComboPedidos();
        atualizarListagem();
        limparResultadoBusca();
    }

    @FXML
    public void initialize() {
        prioridadeCriarCombo.setItems(FXCollections.observableArrayList(EnumPrioridade.values()));
        statusCriarCombo.setItems(FXCollections.observableArrayList(EnumStatus.values()));
        novaPrioridadeCombo.setItems(FXCollections.observableArrayList(EnumPrioridade.values()));
        novoStatusCombo.setItems(FXCollections.observableArrayList(EnumStatus.values()));
        statusBuscaCombo.setItems(FXCollections.observableArrayList(EnumStatus.values()));

        pedidoCombo.setConverter(new javafx.util.StringConverter<Pedido>() {
            @Override
            public String toString(Pedido pedido) {
                if (pedido == null) return null;
                return pedido.getNomeCliente() + " — " + pedido.getDescricaoCarga();
            }
            @Override
            public Pedido fromString(String s) { return null; }
        });

        entregasListView.setItems(entregasObservaveis);
        entregasListView.setCellFactory(lv -> new EntregaListCell());
    }


    @FXML
    private void criarEntrega() {
        try {
            Pedido pedido         = pedidoCombo.getValue();
            EnumPrioridade prioridade = prioridadeCriarCombo.getValue();
            EnumStatus status     = statusCriarCombo.getValue();
            String destino        = destinoField.getText();
            String observacoes    = observacoesField.getText();

            if (pedido == null) {
                exibirAlerta(Alert.AlertType.WARNING, "Campo obrigatório", "Selecione um pedido vinculado.");
                return;
            }
            if (prioridade == null) {
                exibirAlerta(Alert.AlertType.WARNING, "Campo obrigatório", "Selecione a prioridade.");
                return;
            }
            if (status == null) {
                exibirAlerta(Alert.AlertType.WARNING, "Campo obrigatório", "Selecione o status inicial.");
                return;
            }
            if (destino == null || destino.isBlank()) {
                exibirAlerta(Alert.AlertType.WARNING, "Campo obrigatório", "Informe o destino da entrega.");
                return;
            }

            entregaService.criarEntrega(pedido, destino, observacoes, prioridade, status);

            limparCamposCriar();
            atualizarListagem();

        } catch (IllegalArgumentException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro ao criar entrega", e.getMessage());
        }
    }

    private void limparCamposCriar() {
        pedidoCombo.setValue(null);
        prioridadeCriarCombo.setValue(null);
        statusCriarCombo.setValue(null);
        destinoField.clear();
        observacoesField.clear();
    }


    @FXML
    private void alterarRota() {
        UUID id = lerUuid(idAlterarField.getText());

        if (id == null) {
            exibirAlerta(Alert.AlertType.WARNING, "ID inválido", "Informe um ID de entrega válido.");
            return;
        }

        String novoDestino = novoDestinoField.getText();
        if (novoDestino == null || novoDestino.isBlank()) {
            exibirAlerta(Alert.AlertType.WARNING, "Campo obrigatório", "Informe o novo destino.");
            return;
        }

        String novasObs = novasObservacoesField.getText();

        boolean alterado = entregaService.alterarRota(id, novoDestino, novasObs);

        if (!alterado) {
            exibirAlerta(Alert.AlertType.WARNING, "Não encontrado", "Nenhuma entrega com esse ID.");
            return;
        }

        novoDestinoField.clear();
        novasObservacoesField.clear();
        atualizarListagem();
    }


    @FXML
    private void alterarPrioridade() {
        UUID id = lerUuid(idAlterarField.getText());
        EnumPrioridade novaPrioridade = novaPrioridadeCombo.getValue();

        if (id == null) {
            exibirAlerta(Alert.AlertType.WARNING, "ID inválido", "Informe um ID de entrega válido.");
            return;
        }
        if (novaPrioridade == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Campo obrigatório", "Selecione a nova prioridade.");
            return;
        }

        boolean alterado = entregaService.alterarPrioridade(id, novaPrioridade);

        if (!alterado) {
            exibirAlerta(Alert.AlertType.WARNING, "Não encontrado", "Nenhuma entrega com esse ID.");
            return;
        }

        novaPrioridadeCombo.setValue(null);
        atualizarListagem();
    }


    @FXML
    private void alterarStatus() {
        UUID id = lerUuid(idStatusField.getText());
        EnumStatus novoStatus = novoStatusCombo.getValue();

        if (id == null) {
            exibirAlerta(Alert.AlertType.WARNING, "ID inválido", "Informe um ID de entrega válido.");
            return;
        }
        if (novoStatus == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Campo obrigatório", "Selecione o novo status.");
            return;
        }

        boolean alterado = entregaService.alterarStatus(id, novoStatus);

        if (!alterado) {
            exibirAlerta(Alert.AlertType.WARNING, "Não encontrado", "Nenhuma entrega com esse ID.");
            return;
        }

        idStatusField.clear();
        novoStatusCombo.setValue(null);
        atualizarListagem();
    }


    @FXML
    private void buscarPorId() {
        UUID id = lerUuid(idBuscarField.getText());

        if (id == null) {
            exibirAlerta(Alert.AlertType.WARNING, "ID inválido", "Informe um ID de entrega válido.");
            return;
        }

        Entrega entrega = entregaService.buscarPorId(id);

        if (entrega == null) {
            limparResultadoBusca();
            exibirAlerta(Alert.AlertType.INFORMATION, "Não encontrado", "Nenhuma entrega com esse ID.");
            return;
        }

        entregaSelecionada = entrega;

        resultadoPedidoLabel.setText(entrega.getPedidoVinculado().getNomeCliente());
        resultadoDestinoLabel.setText(entrega.getRota().getDestino());
        resultadoPrioridadeBadge.setText(entrega.getPrioridade().name().replace("_", " "));
        setBadgeClasse(resultadoPrioridadeBadge, badgePorPrioridade(entrega.getPrioridade()));
        resultadoStatusBadge.setText(entrega.getStatus().name().replace("_", " "));
        setBadgeClasse(resultadoStatusBadge, badgePorStatus(entrega.getStatus()));
        resultadoDistanciaLabel.setText(entrega.getRota().getDistanciaEstimadaMt() + " km");
        resultadoHorarioLabel.setText("Criada em " + entrega.getHorarioCriacao().format(DATA_FORMATTER));

        exibirIdComBotaoCopiar(entrega.getId());
    }

    private void exibirIdComBotaoCopiar(UUID id) {
        resultadoIdBox.getChildren().clear();

        Label idLabel = new Label("ID: " + id);
        idLabel.getStyleClass().add("next-horario");

        resultadoIdBox.getChildren().add(idLabel);
        resultadoIdBox.getChildren().add(ClipboardUtil.criarBotaoCopiar(id));
    }

    @FXML
    private void concluirEntrega() {
        if (entregaSelecionada == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Nenhuma entrega selecionada",
                    "Busque uma entrega antes de concluir.");
            return;
        }

        boolean concluido = entregaService.concluirEntrega(entregaSelecionada.getId());

        if (!concluido) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível concluir a entrega.");
            return;
        }

        limparResultadoBusca();
        atualizarListagem();
    }

    @FXML
    private void cancelarEntrega() {
        if (entregaSelecionada == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Nenhuma entrega selecionada",
                    "Busque uma entrega antes de cancelar.");
            return;
        }

        boolean cancelado = entregaService.cancelarEntrega(entregaSelecionada.getId());

        if (!cancelado) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível cancelar a entrega.");
            return;
        }

        limparResultadoBusca();
        atualizarListagem();
    }

    @FXML
    private void atualizarListagem() {
        totalEntregasLabel.setText(entregaService.getTotalEntregas() + " ENTREGAS");
        popularComboPedidos();
        aplicarFiltro();
        atualizarEstadoBotoesUndoRedo();
    }

    private void atualizarEstadoBotoesUndoRedo() {
        desfazerButton.setDisable(!entregaService.podeDesfazer());
        refazerButton.setDisable(!entregaService.podeRefazer());
    }

    @FXML
    private void desfazer() {
        var operacao = entregaService.desfazer();
        if (operacao == null) {
            exibirAlerta(Alert.AlertType.INFORMATION, "Nada a desfazer",
                    "Não há operações de entregas para desfazer.");
            return;
        }
        atualizarListagem();
    }

    @FXML
    private void refazer() {
        var operacao = entregaService.refazer();
        if (operacao == null) {
            exibirAlerta(Alert.AlertType.INFORMATION, "Nada a refazer",
                    "Não há operações de entregas para refazer.");
            return;
        }
        atualizarListagem();
    }

    private void popularComboPedidos() {
        Pedido[] pedidos = pedidoService.listarPedidos();
        pedidoCombo.setItems(FXCollections.observableArrayList(pedidos));
    }

    @FXML
    private void aplicarFiltro() {
        Entrega[] resultado;

        if (radioAtivas.isSelected()) {
            resultado = entregaService.listarEntregasAtivas();
        } else if (radioPrioridade.isSelected()) {
            resultado = entregaService.listarOrdenadasPorPrioridade();
        } else if (radioHorario.isSelected()) {
            resultado = entregaService.listarOrdenadasPorHorarioCriacao();
        } else {
            resultado = entregaService.listarTodas();
        }

        popularLista(resultado);
    }

    @FXML
    private void buscarPorStatus() {
        EnumStatus status = statusBuscaCombo.getValue();

        if (status == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Campo obrigatório",
                    "Selecione um status para buscar.");
            return;
        }

        Entrega[] resultado = entregaService.listarPorStatus(status);
        popularLista(resultado);
        filtroGroup.selectToggle(null);
    }

    private void popularLista(Entrega[] entregas) {
        entregasObservaveis.clear();
        if (entregas == null) return;
        for (Entrega e : entregas) {
            if (e != null) entregasObservaveis.add(e);
        }
    }


    private void limparResultadoBusca() {
        entregaSelecionada = null;
        resultadoPedidoLabel.setText("—");
        resultadoDestinoLabel.setText("");
        resultadoPrioridadeBadge.setText("");
        resultadoStatusBadge.setText("");
        resultadoDistanciaLabel.setText("");
        resultadoHorarioLabel.setText("");
        resultadoIdBox.getChildren().clear();
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

    private String badgePorPrioridade(EnumPrioridade prioridade) {
        switch (prioridade) {
            case CRITICA: return "badge-red";
            case ALTA:    return "badge-coral";
            case MEDIA:   return "badge-amber";
            case BAIXA:   return "badge-teal";
            default:      return "badge-gray";
        }
    }

    private String badgePorStatus(EnumStatus status) {
        switch (status) {
            case ENTREGUE:      return "badge-teal";
            case CANCELADA:     return "badge-red";
            case ATRASADA:      return "badge-coral";
            case EM_TRANSITO:   return "badge-amber";
            default:            return "badge-gray";
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