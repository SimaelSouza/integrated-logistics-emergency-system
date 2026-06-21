package br.com.logisticsystem.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Pedido {
    private UUID id;
    private boolean idRestaurado;
    private String nomeCliente;
    private String descricaoCarga;
    private EnumPrioridade prioridade;
    private EnumStatus status;
    private final LocalDateTime horarioCriacao;

    public Pedido(String nomeCliente, String descricaoCarga, EnumPrioridade prioridade) {
        validarNomeCliente(nomeCliente);
        validarDescricaoCarga(descricaoCarga);
        validarPrioridade(prioridade);

        this.id = UUID.randomUUID();
        this.idRestaurado = false;
        this.nomeCliente = nomeCliente;
        this.descricaoCarga = descricaoCarga;
        this.prioridade = prioridade;
        this.status = EnumStatus.PENDENTE;
        this.horarioCriacao = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void restaurarId(UUID idOriginal) {
        if (idRestaurado) {
            throw new IllegalStateException("O ID deste Pedido já foi restaurado anteriormente.");
        }
        if (idOriginal == null) {
            throw new IllegalArgumentException("O ID original é obrigatório para restauração.");
        }
        this.id = idOriginal;
        this.idRestaurado = true;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        validarNomeCliente(nomeCliente);
        this.nomeCliente = nomeCliente;
    }

    public String getDescricaoCarga() {
        return descricaoCarga;
    }

    public void setDescricaoCarga(String descricaoCarga) {
        validarDescricaoCarga(descricaoCarga);
        this.descricaoCarga = descricaoCarga;
    }

    public EnumPrioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(EnumPrioridade prioridade) {
        validarPrioridade(prioridade);
        this.prioridade = prioridade;
    }

    public EnumStatus getStatus() {
        return status;
    }

    public void setStatus(EnumStatus status) {
        validarStatus(status);
        this.status = status;
    }

    public LocalDateTime getHorarioCriacao() {
        return horarioCriacao;
    }

    private void validarNomeCliente(String nomeCliente) {
        if (nomeCliente == null || nomeCliente.isBlank()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório");
        }
    }

    private void validarDescricaoCarga(String descricaoCarga) {
        if (descricaoCarga == null || descricaoCarga.isBlank()) {
            throw new IllegalArgumentException("A descrição da carga é obrigatória");
        }
    }

    private void validarPrioridade(EnumPrioridade prioridade) {
        if (prioridade == null) {
            throw new IllegalArgumentException("A prioridade é obrigatória");
        }
    }

    private void validarStatus(EnumStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("O status é obrigatório");
        }
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", nomeCliente='" + nomeCliente + '\'' +
                ", descricaoCarga='" + descricaoCarga + '\'' +
                ", prioridade=" + prioridade +
                ", status=" + status +
                ", horarioCriacao=" + horarioCriacao +
                '}';
    }
}