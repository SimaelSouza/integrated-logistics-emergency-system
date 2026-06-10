package br.com.logisticsystem.models;

public class Rota {
    private String origem;
    private String destino;
    private int distanciaEstimadaMt;
    private String observacoes;

    public Rota(String origem, String destino, String observacoes) {
        validarOrigem(origem);
        validarDestino(destino);

        this.origem = origem;
        this.destino = destino;
        this.distanciaEstimadaMt = 0;
        this.observacoes = observacoes;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        validarOrigem(origem);
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        validarDestino(destino);
        this.destino = destino;
    }

    public int getDistanciaEstimadaMt() {
        return distanciaEstimadaMt;
    }

    public void setDistanciaEstimadaMt(int distanciaEstimada) {
        if (distanciaEstimada < 0) {
            throw new IllegalArgumentException("A distancia estimada não pode ser negativa.");
        }
        this.distanciaEstimadaMt = distanciaEstimada;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    private void validarOrigem(String origem) {
        if (origem == null || origem.isBlank()) {
            throw new IllegalArgumentException("A origem da entrega é obrigatória");
        }
    }
    private void validarDestino(String destino) {
        if (destino == null || destino.isBlank()) {
            throw new IllegalArgumentException("O destino da entrega é obrigatório");
        }
    }

    @Override
    public String toString() {
        return "Rota{" +
                "origem='" + origem + '\'' +
                ", destino='" + destino + '\'' +
                ", distanciaEstimada=" + distanciaEstimadaMt +
                ", observacoes='" + observacoes + '\'' +
                '}';
    }
}
