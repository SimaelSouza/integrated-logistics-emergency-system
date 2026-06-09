package br.com.logisticsystem.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Entrega {
    private UUID id;
    private Pedido pedidoVinculado;
    private Rota rota;
    private EnumPrioridade prioridade;
    private EnumStatus status;
    private LocalDateTime horarioCriacao;
    private LocalDateTime horarioConclusao;

    public Entrega(Pedido pedidoVinculado, Rota rota, EnumPrioridade prioridade, EnumStatus status) {
        validarPedidoVinculado(pedidoVinculado);
        validarRota(rota);
        validarPrioridade(prioridade);
        validarStatus(status);

        this.id = UUID.randomUUID();
        this.pedidoVinculado = pedidoVinculado;
        this.rota = rota;
        this.prioridade = prioridade;
        this.status = status;
        this.horarioCriacao = LocalDateTime.now();
        this.horarioConclusao = null;
    }

    public UUID getId() {
        return id;
    }

    public Pedido getPedidoVinculado() {
        return pedidoVinculado;
    }

    public void setPedidoVinculado(Pedido pedidoVinculado) {
        validarPedidoVinculado(pedidoVinculado);
        this.pedidoVinculado = pedidoVinculado;
    }

    public Rota getRota() {
        return rota;
    }

    public void setRota(Rota rota) {
        validarRota(rota);
        this.rota = rota;
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

    public LocalDateTime getHorarioConclusao() {
        return horarioConclusao;
    }

    public void setHorarioConclusao(LocalDateTime horarioConclusao) {
        this.horarioConclusao = horarioConclusao;
    }

    private void validarPedidoVinculado(Pedido pedidoVinculado){
        if(pedidoVinculado == null){
            throw new IllegalArgumentException("Pedido vinculado é obrigatório");
        }
    }

    private void validarRota(Rota rota){
        if(rota == null){
            throw new IllegalArgumentException("A rota é obrigatória");
        }
    }

    private void validarPrioridade(EnumPrioridade prioridade){
        if(prioridade == null){
            throw new IllegalArgumentException("A prioridade é obrigatória");
        }
    }

    private void validarStatus(EnumStatus status){
        if(status == null){
            throw new IllegalArgumentException("O status é obrigatório");
        }
    }

    @Override
    public String toString() {
        return "Entrega{" +
                "id=" + id +
                ", pedidoVinculado=" + pedidoVinculado +
                ", rota=" + rota +
                ", prioridade=" + prioridade +
                ", status=" + status +
                ", horarioCriacao=" + horarioCriacao +
                ", horarioConclusao=" + horarioConclusao +
                '}';
    }
}
