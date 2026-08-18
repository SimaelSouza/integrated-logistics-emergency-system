package br.com.logisticsystem.services;

import br.com.logisticsystem.algorithms.efficient.QuickSort;
import br.com.logisticsystem.models.*;
import br.com.logisticsystem.structures.singlylinkedlist.ListaSimples;
import br.com.logisticsystem.structures.stack.Pilha;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Random;
import java.util.UUID;

public class EntregaService {

    private static final int LIMITE_HISTORICO_UNDO_REDO = 50;

    private final ListaSimples<Entrega> entregas;
    private final HistoricoService historicoService;

    private final Pilha<Operacao> pilhaUndo;
    private final Pilha<Operacao> pilhaRedo;

    public EntregaService(HistoricoService historicoService) {
        this.entregas = new ListaSimples<>();
        this.historicoService = historicoService;
        this.pilhaUndo = new Pilha<>(LIMITE_HISTORICO_UNDO_REDO);
        this.pilhaRedo = new Pilha<>(LIMITE_HISTORICO_UNDO_REDO);
    }

    public Rota definirRota(String destino, String observacoes) {
        Random random = new Random();
        Rota rota = new Rota("Central de Distribuição", destino, observacoes);
        rota.setDistanciaEstimadaMt(1 + random.nextInt(1000));
        return rota;
    }


    public Entrega criarEntrega(Pedido pedido, String destino, String observacoes, EnumPrioridade prioridade, EnumStatus status) {
        validarPedido(pedido);
        validarPrioridade(prioridade);
        validarStatus(status);

        Rota rota = definirRota(destino, observacoes);
        validarRota(rota);

        Entrega entrega = new Entrega(pedido, rota, prioridade, status);

        entregas.inserirNoFim(entrega);

        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_ENTREGA,
                "Entrega criada para " + pedido.getNomeCliente() + " com destino a " + destino,
                entrega
        );

        registrarOperacao(new Operacao(
                TipoOperacao.CADASTRAR_ENTREGA,
                entrega.getId(),
                null,
                clonarEstado(entrega),
                "Criação da entrega para " + pedido.getNomeCliente()
        ));

        return entrega;
    }

    public Entrega buscarPorId(UUID id) {

        validarId(id);

        for (int i = 0; i < entregas.getTamanho(); i++) {

            Entrega entrega = entregas.buscar(i);

            if (entrega.getId().equals(id)) {
                return entrega;
            }
        }

        return null;
    }


    public boolean alterarRota(UUID entregaId, String destino, String observacoes) {

        Rota novaRota = definirRota(destino, observacoes);
        validarRota(novaRota);

        Entrega entrega = buscarPorId(entregaId);

        if (entrega == null) {
            return false;
        }

        EstadoEntrega estadoAntes = clonarEstado(entrega);
        String destinoAnterior = entrega.getRota().getDestino();
        entrega.setRota(novaRota);

        historicoService.registrarMovimentacao(
                TipoOperacao.ALTERAR_ROTA_ENTREGA,
                "Rota da entrega de " + entrega.getPedidoVinculado().getNomeCliente()
                        + " alterada de " + destinoAnterior + " para " + destino,
                entrega
        );

        registrarOperacao(new Operacao(
                TipoOperacao.ALTERAR_ROTA_ENTREGA,
                entrega.getId(),
                estadoAntes,
                clonarEstado(entrega),
                "Alteração de rota da entrega de " + entrega.getPedidoVinculado().getNomeCliente()
        ));

        return true;
    }

    public boolean alterarPrioridade(UUID entregaId, EnumPrioridade novaPrioridade) {

        Entrega entrega = buscarPorId(entregaId);

        if (entrega == null) {
            return false;
        }

        validarPrioridade(novaPrioridade);

        EstadoEntrega estadoAntes = clonarEstado(entrega);
        EnumPrioridade prioridadeAnterior = entrega.getPrioridade();
        entrega.setPrioridade(novaPrioridade);

        historicoService.registrarMovimentacao(
                TipoOperacao.ALTERAR_PRIORIDADE_ENTREGA,
                "Prioridade da entrega de " + entrega.getPedidoVinculado().getNomeCliente()
                        + " alterada de " + prioridadeAnterior + " para " + novaPrioridade,
                entrega
        );

        registrarOperacao(new Operacao(
                TipoOperacao.ALTERAR_PRIORIDADE_ENTREGA,
                entrega.getId(),
                estadoAntes,
                clonarEstado(entrega),
                "Alteração de prioridade da entrega de " + entrega.getPedidoVinculado().getNomeCliente()
        ));

        return true;
    }

    public boolean alterarStatus(UUID entregaId, EnumStatus novoStatus) {

        Entrega entrega = buscarPorId(entregaId);

        if (entrega == null) {
            return false;
        }

        validarStatus(novoStatus);

        EstadoEntrega estadoAntes = clonarEstado(entrega);
        EnumStatus statusAnterior = entrega.getStatus();
        entrega.setStatus(novoStatus);

        historicoService.registrarMovimentacao(
                TipoOperacao.ALTERAR_STATUS_ENTREGA,
                "Status da entrega de " + entrega.getPedidoVinculado().getNomeCliente()
                        + " alterado de " + statusAnterior + " para " + novoStatus,
                entrega
        );

        registrarOperacao(new Operacao(
                TipoOperacao.ALTERAR_STATUS_ENTREGA,
                entrega.getId(),
                estadoAntes,
                clonarEstado(entrega),
                "Alteração de status da entrega de " + entrega.getPedidoVinculado().getNomeCliente()
        ));

        return true;
    }


    public boolean cancelarEntrega(UUID entregaId) {

        Entrega entrega = buscarPorId(entregaId);

        if (entrega == null) {
            return false;
        }

        EstadoEntrega estadoAntes = clonarEstado(entrega);
        entrega.setStatus(EnumStatus.CANCELADA);

        historicoService.registrarMovimentacao(
                TipoOperacao.CANCELAR_ENTREGA,
                "Entrega de " + entrega.getPedidoVinculado().getNomeCliente() + " cancelada",
                entrega
        );

        registrarOperacao(new Operacao(
                TipoOperacao.CANCELAR_ENTREGA,
                entrega.getId(),
                estadoAntes,
                clonarEstado(entrega),
                "Cancelamento da entrega de " + entrega.getPedidoVinculado().getNomeCliente()
        ));

        return true;
    }

    public boolean concluirEntrega(UUID entregaId) {

        Entrega entrega = buscarPorId(entregaId);

        if (entrega == null) {
            return false;
        }

        EstadoEntrega estadoAntes = clonarEstado(entrega);
        entrega.setStatus(EnumStatus.ENTREGUE);
        entrega.setHorarioConclusao(LocalDateTime.now());

        historicoService.registrarMovimentacao(
                TipoOperacao.CONCLUIR_ENTREGA,
                "Entrega de " + entrega.getPedidoVinculado().getNomeCliente() + " concluída",
                entrega
        );

        registrarOperacao(new Operacao(
                TipoOperacao.CONCLUIR_ENTREGA,
                entrega.getId(),
                estadoAntes,
                clonarEstado(entrega),
                "Conclusão da entrega de " + entrega.getPedidoVinculado().getNomeCliente()
        ));

        return true;
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

            case CADASTRAR_ENTREGA -> {
                if (undo) {
                    removerSemRegistrarOperacao(operacao.getIdEntidade());
                } else {
                    EstadoEntrega estadoNovo = (EstadoEntrega) operacao.getEstadoNovo();
                    recriarEntrega(operacao.getIdEntidade(), estadoNovo);
                }
            }

            case ALTERAR_ROTA_ENTREGA, ALTERAR_PRIORIDADE_ENTREGA,
                 ALTERAR_STATUS_ENTREGA, CANCELAR_ENTREGA, CONCLUIR_ENTREGA -> {
                Entrega entrega = buscarPorId(operacao.getIdEntidade());
                if (entrega != null && estado != null) {
                    aplicarEstado(entrega, (EstadoEntrega) estado);
                }
            }

            default -> {  }
        }
    }

    private void removerSemRegistrarOperacao(UUID id) {
        for (int i = 0; i < entregas.getTamanho(); i++) {
            if (entregas.buscar(i).getId().equals(id)) {
                entregas.remover(i);
                return;
            }
        }
    }

    private void recriarEntrega(UUID id, EstadoEntrega estado) {
        Entrega entrega = new Entrega(estado.pedidoVinculado, estado.rota, estado.prioridade, estado.status);
        entrega.restaurarId(id);
        entrega.setHorarioConclusao(estado.horarioConclusao);
        entregas.inserirNoFim(entrega);
    }

    private void aplicarEstado(Entrega entrega, EstadoEntrega estado) {
        entrega.setRota(estado.rota);
        entrega.setPrioridade(estado.prioridade);
        entrega.setStatus(estado.status);
        entrega.setHorarioConclusao(estado.horarioConclusao);
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


    private static class EstadoEntrega {
        final Pedido pedidoVinculado;
        final Rota rota;
        final EnumPrioridade prioridade;
        final EnumStatus status;
        final LocalDateTime horarioConclusao;

        EstadoEntrega(Pedido pedidoVinculado, Rota rota, EnumPrioridade prioridade,
                      EnumStatus status, LocalDateTime horarioConclusao) {
            this.pedidoVinculado = pedidoVinculado;
            this.rota = rota;
            this.prioridade = prioridade;
            this.status = status;
            this.horarioConclusao = horarioConclusao;
        }
    }

    private EstadoEntrega clonarEstado(Entrega entrega) {
        return new EstadoEntrega(
                entrega.getPedidoVinculado(),
                entrega.getRota(),
                entrega.getPrioridade(),
                entrega.getStatus(),
                entrega.getHorarioConclusao()
        );
    }

    public Entrega[] listarTodas() {

        Entrega[] resultado =
                new Entrega[entregas.getTamanho()];

        for (int i = 0; i < entregas.getTamanho(); i++) {
            resultado[i] = entregas.buscar(i);
        }

        return resultado;
    }

    public Entrega[] listarEntregasAtivas() {

        int contador = 0;

        for (int i = 0; i < entregas.getTamanho(); i++) {

            EnumStatus status =
                    entregas.buscar(i).getStatus();

            if (status != EnumStatus.ENTREGUE
                    && status != EnumStatus.CANCELADA) {

                contador++;
            }
        }

        Entrega[] resultado =
                new Entrega[contador];

        int indice = 0;

        for (int i = 0; i < entregas.getTamanho(); i++) {

            Entrega entrega = entregas.buscar(i);

            if (entrega.getStatus() != EnumStatus.ENTREGUE
                    && entrega.getStatus() != EnumStatus.CANCELADA) {

                resultado[indice++] = entrega;
            }
        }

        return resultado;
    }

    public Entrega[] listarOrdenadasPorPrioridade() {

        Entrega[] entregasOrdenadas = listarTodas();

        QuickSort.sort(
                entregasOrdenadas,
                Comparator.comparingInt(
                        p -> p.getPrioridade().getNivel()
                )
        );

        return entregasOrdenadas;
    }

    public Entrega[] listarPorStatus(EnumStatus status) {

        validarStatus(status);

        int contador = 0;

        for (int i = 0; i < entregas.getTamanho(); i++) {

            if (entregas.buscar(i).getStatus() == status) {
                contador++;
            }
        }

        Entrega[] resultado = new Entrega[contador];

        int indice = 0;

        for (int i = 0; i < entregas.getTamanho(); i++) {

            Entrega entrega = entregas.buscar(i);

            if (entrega.getStatus() == status) {
                resultado[indice++] = entrega;
            }
        }

        return resultado;
    }

    public Entrega[] listarOrdenadasPorHorarioCriacao() {

        Entrega[] entregasOrdenadas = listarTodas();

        QuickSort.sort(
                entregasOrdenadas,
                Comparator.comparing(
                        Entrega::getHorarioCriacao
                )
        );

        return entregasOrdenadas;
    }

    public int getTotalEntregas() {
        return entregas.getTamanho();
    }

    public boolean estaVazio() {
        return entregas.estaVazia();
    }


    private void validarId(UUID id) {

        if (id == null) {
            throw new IllegalArgumentException("ID é obrigatório.");
        }
    }

    private void validarPedido(Pedido pedido) {

        if (pedido == null) {
            throw new IllegalArgumentException("Pedido é obrigatório.");
        }
    }

    private void validarRota(Rota rota) {

        if (rota == null) {
            throw new IllegalArgumentException("Rota é obrigatória.");
        }
    }

    private void validarPrioridade(EnumPrioridade prioridade) {
        if (prioridade == null) {
            throw new IllegalArgumentException("Prioridade é obrigatória.");
        }
    }

    private void validarStatus(EnumStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status é obrigatório.");
        }
    }
}