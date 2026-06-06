package br.com.logisticsystem.models;

public enum EnumPrioridade {
    SEM_PRIORIDADE(0),
    BAIXA(1),
    MEDIA(2),
    ALTA(3),
    CRITICA(4);

    private final int nivel;

    EnumPrioridade(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }
}
