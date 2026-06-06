package br.com.logisticsystem.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Atendimento {

    private final UUID id;
    private String nomeSolicitante;
    private EnumPrioridade prioridade;
    private LocalDateTime horarioSolicitacao;
    private EnumTipo tipoOcorrencia;

    public Atendimento(String nomeSolicitante, EnumPrioridade prioridade,
                       LocalDateTime horarioSolicitacao, EnumTipo tipoOcorrencia) {
        validarNomeSolicitante(nomeSolicitante);
        validarPrioridade(prioridade);
        validarHorarioSolicitacao(horarioSolicitacao);
        validarTipoOcorrencia(tipoOcorrencia);

        this.id = UUID.randomUUID();
        this.nomeSolicitante = nomeSolicitante;
        this.prioridade = prioridade;
        this.horarioSolicitacao = horarioSolicitacao;
        this.tipoOcorrencia = tipoOcorrencia;
    }

    public UUID getId() {
        return id;
    }

    public String getNomeSolicitante() {
        return nomeSolicitante;
    }

    public void setNomeSolicitante(String nomeSolicitante) {
        validarNomeSolicitante(nomeSolicitante);
        this.nomeSolicitante = nomeSolicitante;
    }

    public EnumPrioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(EnumPrioridade prioridade) {
        validarPrioridade(prioridade);
        this.prioridade = prioridade;
    }

    public LocalDateTime getHorarioSolicitacao() {
        return horarioSolicitacao;
    }

    public void setHorarioSolicitacao(LocalDateTime horarioSolicitacao) {
        validarHorarioSolicitacao(horarioSolicitacao);
        this.horarioSolicitacao = horarioSolicitacao;
    }

    public EnumTipo getTipoOcorrencia() {
        return tipoOcorrencia;
    }

    public void setTipoOcorrencia(EnumTipo tipoOcorrencia) {
        validarTipoOcorrencia(tipoOcorrencia);
        this.tipoOcorrencia = tipoOcorrencia;
    }

    private void validarNomeSolicitante(String nomeSolicitante) {
        if (nomeSolicitante == null || nomeSolicitante.isBlank()) {
            throw new IllegalArgumentException("Nome do solicitante e obrigatorio.");
        }
    }

    private void validarPrioridade(EnumPrioridade prioridade) {
        if (prioridade == null) {
            throw new IllegalArgumentException("Prioridade e obrigatoria.");
        }
    }

    private void validarHorarioSolicitacao(LocalDateTime horarioSolicitacao) {
        if (horarioSolicitacao == null) {
            throw new IllegalArgumentException("Horario da solicitacao e obrigatorio.");
        }
    }

    private void validarTipoOcorrencia(EnumTipo tipoOcorrencia) {
        if (tipoOcorrencia == null) {
            throw new IllegalArgumentException("Tipo da ocorrencia e obrigatorio.");
        }
    }

    @Override
    public String toString() {
        return "Atendimento{" +
                "id=" + id +
                ", nomeSolicitante='" + nomeSolicitante + '\'' +
                ", prioridade=" + prioridade +
                ", horarioSolicitacao=" + horarioSolicitacao +
                ", tipoOcorrencia=" + tipoOcorrencia +
                '}';
    }
}
