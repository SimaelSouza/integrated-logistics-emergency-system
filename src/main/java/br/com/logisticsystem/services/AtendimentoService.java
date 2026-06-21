package br.com.logisticsystem.services;

import br.com.logisticsystem.models.Atendimento;
import br.com.logisticsystem.models.EnumPrioridade;
import br.com.logisticsystem.models.EnumTipo;
import br.com.logisticsystem.models.Operacao;
import br.com.logisticsystem.models.TipoOperacao;
import br.com.logisticsystem.structures.queue.Fila;
import br.com.logisticsystem.structures.queue.FilaPrioritaria;
import br.com.logisticsystem.structures.stack.Pilha;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class AtendimentoService {

    private static final int CAPACIDADE_PADRAO = 100;
    private static final int LIMITE_HISTORICO_UNDO_REDO = 50;

    private final Fila filaComum;
    private final FilaPrioritaria filaPrioritaria;
    private final HistoricoService historicoService;

    private final Pilha<Operacao> pilhaUndo;
    private final Pilha<Operacao> pilhaRedo;

    private long totalAtendimentos;
    private long somaTemposEsperaSegundos;

    public AtendimentoService(HistoricoService historicoService) {
        this.filaComum = new Fila(CAPACIDADE_PADRAO);
        this.filaPrioritaria = new FilaPrioritaria(CAPACIDADE_PADRAO);
        this.historicoService = historicoService;
        this.pilhaUndo = new Pilha<>(LIMITE_HISTORICO_UNDO_REDO);
        this.pilhaRedo = new Pilha<>(LIMITE_HISTORICO_UNDO_REDO);
        this.totalAtendimentos = 0;
        this.somaTemposEsperaSegundos = 0;
    }

    public AtendimentoService(int capacidade, HistoricoService historicoService) {
        this.filaComum = new Fila(capacidade);
        this.filaPrioritaria = new FilaPrioritaria(capacidade);
        this.historicoService = historicoService;
        this.pilhaUndo = new Pilha<>(LIMITE_HISTORICO_UNDO_REDO);
        this.pilhaRedo = new Pilha<>(LIMITE_HISTORICO_UNDO_REDO);
        this.totalAtendimentos = 0;
        this.somaTemposEsperaSegundos = 0;
    }


    public Atendimento cadastrar(String nomeSolicitante, EnumPrioridade prioridade, EnumTipo tipoOcorrencia) {
        validarNomeSolicitante(nomeSolicitante);
        validarPrioridade(prioridade);
        validarTipoOcorrencia(tipoOcorrencia);

        Atendimento atendimento = new Atendimento(nomeSolicitante, prioridade, tipoOcorrencia);
        direcionarParaFila(atendimento);

        historicoService.registrarMovimentacao(
                TipoOperacao.CADASTRAR_ATENDIMENTO,
                "Atendimento de " + nomeSolicitante + " cadastrado (" + prioridade + ")",
                atendimento
        );

        registrarOperacao(new Operacao(
                TipoOperacao.CADASTRAR_ATENDIMENTO,
                atendimento.getId(),
                null,
                clonarEstado(atendimento),
                "Cadastro do atendimento de " + nomeSolicitante
        ));

        return atendimento;
    }

    private void direcionarParaFila(Atendimento atendimento) {
        if (atendimento.getPrioridade() == EnumPrioridade.SEM_PRIORIDADE) {
            filaComum.enqueue(atendimento);
        } else {
            filaPrioritaria.enqueue(atendimento);
        }
    }

    public Atendimento atenderProximo() {
        Atendimento atendido;

        if (!filaPrioritaria.isEmpty()) {
            atendido = filaPrioritaria.dequeue();
        } else if (!filaComum.isEmpty()) {
            atendido = filaComum.dequeue();
        } else {
            return null;
        }

        registrarTempoEspera(atendido);

        historicoService.registrarMovimentacao(
                TipoOperacao.ATENDER_ATENDIMENTO,
                "Atendimento de " + atendido.getNomeSolicitante() + " concluído",
                atendido
        );

        return atendido;
    }

    private void registrarTempoEspera(Atendimento atendido) {
        long segundosEspera = Duration
                .between(atendido.getHorarioSolicitacao(), LocalDateTime.now())
                .getSeconds();

        somaTemposEsperaSegundos += segundosEspera;
        totalAtendimentos++;
    }


    public boolean alterarPrioridade(Atendimento atendimento, EnumPrioridade novaPrioridade) {
        validarPrioridade(novaPrioridade);

        EstadoAtendimento estadoAntes = clonarEstado(atendimento);
        EnumPrioridade prioridadeAnterior = atendimento.getPrioridade();

        boolean removido = removerDaFila(atendimento);

        if (!removido) {
            return false;
        }

        atendimento.setPrioridade(novaPrioridade);
        direcionarParaFila(atendimento);

        historicoService.registrarMovimentacao(
                TipoOperacao.ALTERAR_PRIORIDADE_ATENDIMENTO,
                "Prioridade de " + atendimento.getNomeSolicitante()
                        + " alterada de " + prioridadeAnterior + " para " + novaPrioridade,
                atendimento
        );

        registrarOperacao(new Operacao(
                TipoOperacao.ALTERAR_PRIORIDADE_ATENDIMENTO,
                atendimento.getId(),
                estadoAntes,
                clonarEstado(atendimento),
                "Alteração de prioridade do atendimento de " + atendimento.getNomeSolicitante()
        ));

        return true;
    }

    public boolean removerDaFila(Atendimento atendimento) {
        if (removerDaFilaPrioritaria(atendimento)) {
            return true;
        }
        return removerDaFilaComum(atendimento);
    }

    private boolean removerDaFilaPrioritaria(Atendimento alvo) {
        if (filaPrioritaria.isEmpty()) {
            return false;
        }

        int tamanho = filaPrioritaria.getTamanho();
        Atendimento[] temp = new Atendimento[tamanho];
        boolean encontrado = false;

        for (int i = 0; i < tamanho; i++) {
            Atendimento atual = filaPrioritaria.dequeue();
            if (!encontrado && atual.getId().equals(alvo.getId())) {
                encontrado = true;
            } else {
                temp[i] = atual;
            }
        }

        for (Atendimento a : temp) {
            if (a != null) {
                filaPrioritaria.enqueue(a);
            }
        }

        return encontrado;
    }

    private boolean removerDaFilaComum(Atendimento alvo) {
        if (filaComum.isEmpty()) {
            return false;
        }

        int tamanho = filaComum.getTamanho();
        Atendimento[] temp = new Atendimento[tamanho];
        boolean encontrado = false;

        for (int i = 0; i < tamanho; i++) {
            Atendimento atual = filaComum.dequeue();
            if (!encontrado && atual.getId().equals(alvo.getId())) {
                encontrado = true;
            } else {
                temp[i] = atual;
            }
        }

        for (Atendimento a : temp) {
            if (a != null) {
                filaComum.enqueue(a);
            }
        }

        return encontrado;
    }

    public boolean remover(Atendimento atendimento) {
        return removerDaFila(atendimento);
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
        switch (operacao.getTipoOperacao()) {

            case CADASTRAR_ATENDIMENTO -> {
                if (undo) {
                    removerPorIdSemRegistrar(operacao.getIdEntidade());
                } else {
                    EstadoAtendimento estadoNovo = (EstadoAtendimento) operacao.getEstadoNovo();
                    recriarAtendimento(operacao.getIdEntidade(), estadoNovo);
                }
            }

            case ALTERAR_PRIORIDADE_ATENDIMENTO -> {
                Object estado = undo ? operacao.getEstadoAnterior() : operacao.getEstadoNovo();
                if (estado == null) break;

                EstadoAtendimento estadoAlvo = (EstadoAtendimento) estado;
                Atendimento atendimento = buscarPorId(operacao.getIdEntidade());

                if (atendimento != null) {
                    removerDaFila(atendimento);
                    atendimento.setPrioridade(estadoAlvo.prioridade);
                    direcionarParaFila(atendimento);
                }
            }

            default -> {  }
        }
    }

    private void removerPorIdSemRegistrar(UUID id) {
        Atendimento atendimento = buscarPorId(id);
        if (atendimento != null) {
            removerDaFila(atendimento);
        }
    }

    private void recriarAtendimento(UUID id, EstadoAtendimento estado) {
        Atendimento atendimento = new Atendimento(estado.nomeSolicitante, estado.prioridade, estado.tipoOcorrencia);
        atendimento.restaurarId(id);
        direcionarParaFila(atendimento);
    }

    private Atendimento buscarPorId(UUID id) {
        for (Atendimento a : filaPrioritaria.listar()) {
            if (a != null && a.getId().equals(id)) {
                return a;
            }
        }
        for (Atendimento a : filaComum.listar()) {
            if (a != null && a.getId().equals(id)) {
                return a;
            }
        }
        return null;
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

    private static class EstadoAtendimento {
        final String nomeSolicitante;
        final EnumPrioridade prioridade;
        final EnumTipo tipoOcorrencia;

        EstadoAtendimento(String nomeSolicitante, EnumPrioridade prioridade, EnumTipo tipoOcorrencia) {
            this.nomeSolicitante = nomeSolicitante;
            this.prioridade = prioridade;
            this.tipoOcorrencia = tipoOcorrencia;
        }
    }

    private EstadoAtendimento clonarEstado(Atendimento atendimento) {
        return new EstadoAtendimento(
                atendimento.getNomeSolicitante(),
                atendimento.getPrioridade(),
                atendimento.getTipoOcorrencia()
        );
    }


    public double calcularTempoMedioEsperaSegundos() {
        if (totalAtendimentos == 0) {
            return 0;
        }
        return (double) somaTemposEsperaSegundos / totalAtendimentos;
    }

    public long getTotalAtendimentos() {
        return totalAtendimentos;
    }


    public Atendimento[] listarFilaComum() {
        return filaComum.listar();
    }

    public Atendimento[] listarFilaPrioritaria() {
        return filaPrioritaria.listar();
    }

    public int getTotalPendentes() {
        return filaComum.getTamanho() + filaPrioritaria.getTamanho();
    }

    public boolean filaVazia() {
        return filaComum.isEmpty() && filaPrioritaria.isEmpty();
    }

    public Atendimento consultarProximo() {
        if (!filaPrioritaria.isEmpty()) {
            return filaPrioritaria.peek();
        }
        return filaComum.peek();
    }


    private void validarNomeSolicitante(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do solicitante é obrigatório.");
        }
    }

    private void validarPrioridade(EnumPrioridade prioridade) {
        if (prioridade == null) {
            throw new IllegalArgumentException("Prioridade é obrigatória.");
        }
    }

    private void validarTipoOcorrencia(EnumTipo tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de ocorrência é obrigatório.");
        }
    }
}