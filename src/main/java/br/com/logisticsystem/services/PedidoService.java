package br.com.logisticsystem.services;

import br.com.logisticsystem.algorithms.efficient.QuickSort;
import br.com.logisticsystem.exceptions.EstruturaVaziaException;
import br.com.logisticsystem.models.*;
import br.com.logisticsystem.structures.singlylinkedlist.ListaSimples;
import br.com.logisticsystem.structures.stack.Pilha;

import java.util.Comparator;
import java.util.UUID;

public class PedidoService {

    private static final int LIMITE_HISTORICO_UNDO_REDO = 50;

    private final ListaSimples<Pedido> pedidos;
    private final HistoricoService historicoService;

    private final Pilha<Operacao> pilhaUndo;
    private final Pilha<Operacao> pilhaRedo;

    public PedidoService(HistoricoService historicoService) {
        this.pedidos = new ListaSimples<>();
        this.historicoService = historicoService;
        this.pilhaUndo = new Pilha<>(LIMITE_HISTORICO_UNDO_REDO);
        this.pilhaRedo = new Pilha<>(LIMITE_HISTORICO_UNDO_REDO);
    }


    public Pedido cadastrar(String nomeCliente, String descricaoCarga, EnumPrioridade prioridade) {
        validarNome(nomeCliente);
        validarDescricao(descricaoCarga);
        validarPrioridade(prioridade);

        Pedido pedido = new Pedido(nomeCliente, descricaoCarga, prioridade);

        pedidos.inserirNoFim(pedido);

        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_PEDIDO,
                "Pedido de " + nomeCliente + " cadastrado (" + prioridade + ")",
                pedido
        );

        registrarOperacao(new Operacao(
                TipoOperacao.CADASTRAR_PEDIDO,
                pedido.getId(),
                null,
                clonarEstado(pedido),
                "Cadastro do pedido de " + nomeCliente
        ));

        return pedido;
    }

    public Pedido buscarPorId(UUID id) {

        for (int i = 0; i < pedidos.getTamanho(); i++) {

            Pedido atual = pedidos.buscar(i);

            if (atual.getId().equals(id)) {
                return atual;
            }
        }

        return null;
    }


    public boolean atualizar(UUID id, String descricaoCarga, EnumPrioridade prioridade) {

        for (int i = 0; i < pedidos.getTamanho(); i++) {

            Pedido pedido = pedidos.buscar(i);

            if (pedido.getId().equals(id)) {

                EstadoPedido estadoAntes = clonarEstado(pedido);

                if (!(descricaoCarga == null || descricaoCarga.isBlank())) {
                    pedido.setDescricaoCarga(descricaoCarga);
                }
                if (!(prioridade == null)) {
                    pedido.setPrioridade(prioridade);
                }

                historicoService.registrarMovimentacao(
                        TipoOperacao.ATUALIZAR_PEDIDO,
                        "Pedido de " + pedido.getNomeCliente() + " atualizado",
                        pedido
                );

                registrarOperacao(new Operacao(
                        TipoOperacao.ATUALIZAR_PEDIDO,
                        pedido.getId(),
                        estadoAntes,
                        clonarEstado(pedido),
                        "Atualização do pedido de " + pedido.getNomeCliente()
                ));

                return true;
            }
        }

        return false;
    }

    public boolean alterarStatus(UUID id, EnumStatus novoStatus) {
        Pedido pedido = buscarPorId(id);

        if (pedido == null) {
            return false;
        }

        validarStatus(novoStatus);

        EstadoPedido estadoAntes = clonarEstado(pedido);
        EnumStatus statusAnterior = pedido.getStatus();
        pedido.setStatus(novoStatus);

        historicoService.registrarMovimentacao(
                TipoOperacao.ALTERAR_STATUS_PEDIDO,
                "Status do pedido de " + pedido.getNomeCliente()
                        + " alterado de " + statusAnterior + " para " + novoStatus,
                pedido
        );

        registrarOperacao(new Operacao(
                TipoOperacao.ALTERAR_STATUS_PEDIDO,
                pedido.getId(),
                estadoAntes,
                clonarEstado(pedido),
                "Alteração de status do pedido de " + pedido.getNomeCliente()
        ));

        return true;
    }


    public boolean remover(UUID id) {

        for (int i = 0; i < pedidos.getTamanho(); i++) {

            Pedido pedido = pedidos.buscar(i);

            if (pedido.getId().equals(id)) {

                EstadoPedido estadoAntes = clonarEstado(pedido);

                pedidos.remover(i);

                historicoService.registrarMovimentacao(
                        TipoOperacao.REMOVER_PEDIDO,
                        "Pedido de " + pedido.getNomeCliente() + " removido",
                        pedido
                );

                registrarOperacao(new Operacao(
                        TipoOperacao.REMOVER_PEDIDO,
                        pedido.getId(),
                        estadoAntes,
                        null,
                        "Remoção do pedido de " + pedido.getNomeCliente()
                ));

                return true;
            }
        }

        return false;
    }


    private void registrarOperacao(Operacao operacao) {
        pilhaRedo.limpar();

        if (pilhaUndo.isFull()) {
            return;
        }

        pilhaUndo.push(operacao);
    }


    public Operacao desfazer() {
        if (pilhaUndo.isEmpty()) {
            return null;
        }

        Operacao operacao = pilhaUndo.pop();
        aplicarReversao(operacao, true);
        pilhaRedo.push(operacao);

        return operacao;
    }


    public Operacao refazer() {
        if (pilhaRedo.isEmpty()) {
            return null;
        }

        Operacao operacao = pilhaRedo.pop();
        aplicarReversao(operacao, false);
        pilhaUndo.push(operacao);

        return operacao;
    }


    private void aplicarReversao(Operacao operacao, boolean undo) {
        Object estado = undo ? operacao.getEstadoAnterior() : operacao.getEstadoNovo();

        switch (operacao.getTipoOperacao()) {

            case CADASTRAR_PEDIDO -> {
                if (undo) {
                    removerSemRegistrarOperacao(operacao.getIdEntidade());
                } else {
                    EstadoPedido estadoNovo = (EstadoPedido) operacao.getEstadoNovo();
                    recriarPedido(operacao.getIdEntidade(), estadoNovo);
                }
            }

            case ATUALIZAR_PEDIDO, ALTERAR_STATUS_PEDIDO -> {
                Pedido pedido = buscarPorId(operacao.getIdEntidade());
                if (pedido != null && estado != null) {
                    aplicarEstado(pedido, (EstadoPedido) estado);
                }
            }

            case REMOVER_PEDIDO -> {
                if (undo) {
                    EstadoPedido estadoAnterior = (EstadoPedido) operacao.getEstadoAnterior();
                    recriarPedido(operacao.getIdEntidade(), estadoAnterior);
                } else {
                    removerSemRegistrarOperacao(operacao.getIdEntidade());
                }
            }

            default -> { }
        }
    }

    private void removerSemRegistrarOperacao(UUID id) {
        for (int i = 0; i < pedidos.getTamanho(); i++) {
            if (pedidos.buscar(i).getId().equals(id)) {
                pedidos.remover(i);
                return;
            }
        }
    }

    private void recriarPedido(UUID id, EstadoPedido estado) {
        Pedido pedido = new Pedido(estado.nomeCliente, estado.descricaoCarga, estado.prioridade);
        pedido.restaurarId(id);
        pedido.setStatus(estado.status);
        pedidos.inserirNoFim(pedido);
    }

    private void aplicarEstado(Pedido pedido, EstadoPedido estado) {
        pedido.setDescricaoCarga(estado.descricaoCarga);
        pedido.setPrioridade(estado.prioridade);
        pedido.setStatus(estado.status);
    }

    public boolean podeDesfazer() {
        return !pilhaUndo.isEmpty();
    }

    public boolean podeRefazer() {
        return !pilhaRedo.isEmpty();
    }

    public int getTamanhoPilhaUndo() {
        return pilhaUndo.getTamanho();
    }

    public int getTamanhoPilhaRedo() {
        return pilhaRedo.getTamanho();
    }


    private static class EstadoPedido {
        final String nomeCliente;
        final String descricaoCarga;
        final EnumPrioridade prioridade;
        final EnumStatus status;

        EstadoPedido(String nomeCliente, String descricaoCarga, EnumPrioridade prioridade, EnumStatus status) {
            this.nomeCliente = nomeCliente;
            this.descricaoCarga = descricaoCarga;
            this.prioridade = prioridade;
            this.status = status;
        }
    }

    private EstadoPedido clonarEstado(Pedido pedido) {
        return new EstadoPedido(
                pedido.getNomeCliente(),
                pedido.getDescricaoCarga(),
                pedido.getPrioridade(),
                pedido.getStatus()
        );
    }


    public Pedido[] listarPedidos() {

        Pedido[] resultado = new Pedido[pedidos.getTamanho()];

        for (int i = 0; i < pedidos.getTamanho(); i++) {

            resultado[i] = pedidos.buscar(i);
        }

        return resultado;
    }

    public Pedido[] listarPedidosAtivos() {

        int contador = 0;

        for (int i = 0; i < pedidos.getTamanho(); i++) {

            Pedido pedido = pedidos.buscar(i);

            if (pedido.getStatus() != EnumStatus.ENTREGUE
                    && pedido.getStatus() != EnumStatus.CANCELADA) {

                contador++;
            }
        }

        Pedido[] ativos = new Pedido[contador];

        int indice = 0;

        for (int i = 0; i < pedidos.getTamanho(); i++) {

            Pedido pedido = pedidos.buscar(i);

            if (pedido.getStatus() != EnumStatus.ENTREGUE
                    && pedido.getStatus() != EnumStatus.CANCELADA) {

                ativos[indice++] = pedido;
            }
        }

        return ativos;
    }

    public Pedido[] listarOrdenadosPorPrioridade() {

        Pedido[] pedidosOrdenados = listarPedidos();

        QuickSort.sort(
                pedidosOrdenados,
                Comparator.comparingInt(
                        p -> p.getPrioridade().getNivel()
                )
        );

        return pedidosOrdenados;
    }

    public Pedido[] listarOrdenadosPorHorario() {

        Pedido[] pedidosOrdenados = listarPedidos();

        QuickSort.sort(
                pedidosOrdenados,
                Comparator.comparing(
                        Pedido::getHorarioCriacao
                )
        );

        return pedidosOrdenados;
    }

    public Pedido[] listarPorStatus(EnumStatus status) {

        validarStatus(status);

        int contador = 0;

        for (int i = 0; i < pedidos.getTamanho(); i++) {

            if (pedidos.buscar(i).getStatus() == status) {
                contador++;
            }
        }

        Pedido[] resultado = new Pedido[contador];

        int indice = 0;

        for (int i = 0; i < pedidos.getTamanho(); i++) {

            Pedido pedido = pedidos.buscar(i);

            if (pedido.getStatus() == status) {
                resultado[indice++] = pedido;
            }
        }

        return resultado;
    }

    public int getTotalPedidos() {
        return pedidos.getTamanho();
    }

    public boolean estaVazio() {
        return pedidos.estaVazia();
    }


    private void validarId(UUID id) {

        if (id == null) {
            throw new IllegalArgumentException(
                    "ID é obrigatório."
            );
        }
    }

    private void validarNome(String nome) {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "Nome do cliente é obrigatório."
            );
        }
    }

    private void validarDescricao(String descricao) {

        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException(
                    "Descrição da carga é obrigatória."
            );
        }
    }

    private void validarPrioridade(
            EnumPrioridade prioridade
    ) {

        if (prioridade == null) {
            throw new IllegalArgumentException(
                    "Prioridade é obrigatória."
            );
        }
    }

    private void validarStatus(
            EnumStatus status
    ) {

        if (status == null) {
            throw new IllegalArgumentException(
                    "Status é obrigatório."
            );
        }
    }
}