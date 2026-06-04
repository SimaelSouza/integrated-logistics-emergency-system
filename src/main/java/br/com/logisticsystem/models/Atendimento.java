package br.com.logisticsystem.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Atendimento {

    private final UUID id;

    private String nomeSolicitante;

    private EnumPrioridade prioridade;

    private LocalDateTime horario;

    private EnumTipo tipo;

    public Atendimento(String nomeSolicitante, EnumPrioridade prioridade, LocalDateTime horario, EnumTipo tipo) {
        if (nomeSolicitante == null || nomeSolicitante.isBlank()) {
            throw new IllegalArgumentException("Nome do solicitante é obrigatório.");
        }

        this.id = UUID.randomUUID();
        this.nomeSolicitante = nomeSolicitante;
        this.prioridade = prioridade;
        this.horario = horario;
        this.tipo = tipo;
    }

    public UUID getId() {
        return id;
    }


    public String getNomeSolicitante() {
        return nomeSolicitante;
    }

    public void setNomeSolicitante(String nomeSolicitante) {
        this.nomeSolicitante = nomeSolicitante;
    }

    public EnumPrioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(EnumPrioridade prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public EnumTipo getTipo() {
        return tipo;
    }

    public void setTipo(EnumTipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Atendimento{" +
                "id=" + id +
                ", nomeSolicitante='" + nomeSolicitante + '\'' +
                ", prioridade=" + prioridade +
                ", horario=" + horario +
                ", tipo=" + tipo +
                '}';
    }
}
