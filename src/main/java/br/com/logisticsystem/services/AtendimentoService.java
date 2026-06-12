package br.com.logisticsystem.services;

import br.com.logisticsystem.models.Atendimento;
import br.com.logisticsystem.models.EnumPrioridade;
import br.com.logisticsystem.models.EnumTipo;
import br.com.logisticsystem.structures.queue.Fila;
import br.com.logisticsystem.structures.queue.FilaPrioritaria;

import java.time.Duration;
import java.time.LocalDateTime;

public class AtendimentoService {

    private static final int CAPACIDADE_PADRAO = 100;

    private final Fila filaComum;
    private final FilaPrioritaria filaPrioritaria;

    private long totalAtendimentos;
    private long somaTemposEsperaSegundos;

    public AtendimentoService() {
        this.filaComum = new Fila(CAPACIDADE_PADRAO);
        this.filaPrioritaria = new FilaPrioritaria(CAPACIDADE_PADRAO);
        this.totalAtendimentos = 0;
        this.somaTemposEsperaSegundos = 0;
    }

    public AtendimentoService(int capacidade) {
        this.filaComum = new Fila(capacidade);
        this.filaPrioritaria = new FilaPrioritaria(capacidade);
        this.totalAtendimentos = 0;
        this.somaTemposEsperaSegundos = 0;
    }

    public Atendimento cadastrar(String nomeSolicitante, EnumPrioridade prioridade, EnumTipo tipoOcorrencia) {
        validarNomeSolicitante(nomeSolicitante);
        validarPrioridade(prioridade);
        validarTipoOcorrencia(tipoOcorrencia);

        Atendimento atendimento = new Atendimento(nomeSolicitante, prioridade, tipoOcorrencia);
        direcionarParaFila(atendimento);
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

        boolean removido = removerDaFila(atendimento);

        if (!removido) {
            return false;
        }

        atendimento.setPrioridade(novaPrioridade);
        direcionarParaFila(atendimento);
        return true;
    }

    private boolean removerDaFila(Atendimento atendimento) {
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
