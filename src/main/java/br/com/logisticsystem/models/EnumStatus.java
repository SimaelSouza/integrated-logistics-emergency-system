package br.com.logisticsystem.models;

public enum EnumStatus {
    PENDENTE(0),
    EM_PREPARACAO(1),
    DESPACHADA(2),
    EM_TRANSITO(3),
    ENTREGUE(4),
    ATRASADA(5),
    CANCELADA(6);

    private final int nivel;

    EnumStatus(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }
}
