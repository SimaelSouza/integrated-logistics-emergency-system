package br.com.logisticsystem.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Atendimento {

    private UUID id;
    private boolean idRestaurado;

    private String nomeSolicitante;
    private EnumPrioridade prioridade;
    private final LocalDateTime horarioSolicitacao;
    private EnumTipo tipoOcorrencia;

    public Atendimento(String nomeSolicitante, EnumPrioridade prioridade, EnumTipo tipoOcorrencia) {
        validarNomeSolicitante(nomeSolicitante);
        validarPrioridade(prioridade);
        validarTipoOcorrencia(tipoOcorrencia);

        this.id = UUID.randomUUID();
        this.idRestaurado = false;
        this.nomeSolicitante = nomeSolicitante;
        this.prioridade = prioridade;
        this.horarioSolicitacao = LocalDateTime.now();
        this.tipoOcorrencia = tipoOcorrencia;
    }

    public UUID getId() {
        return id;
    }

    public void restaurarId(UUID idOriginal) {
        if (idRestaurado) {
            throw new IllegalStateException("O ID deste Atendimento já foi restaurado anteriormente.");
        }
        if (idOriginal == null) {
            throw new IllegalArgumentException("O ID original é obrigatório para restauração.");
        }
        this.id = idOriginal;
        this.idRestaurado = true;
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