package br.com.logisticsystem.structures.stack;

public class NoPilha<T> {

    private T valor;
    private NoPilha<T> proximo;

    public NoPilha(T valor) {
        this.valor = valor;
        this.proximo = null;
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public NoPilha<T> getProximo() {
        return proximo;
    }

    public void setProximo(NoPilha<T> proximo) {
        this.proximo = proximo;
    }
}
