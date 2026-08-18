package br.com.logisticsystem.structures.singlylinkedlist;

public class NoSimples<T> {
    private T elemento;
    private NoSimples<T> proximo;

    public NoSimples(T elemento) {
        this.elemento = elemento;
        this.proximo = null;
    }

    public NoSimples(T elemento, NoSimples<T> proximo) {
        this.elemento = elemento;
        this.proximo = proximo;
    }

    public T getElemento() {
        return elemento;
    }

    public void setElemento(T elemento) {
        this.elemento = elemento;
    }

    public NoSimples<T> getProximo() {
        return proximo;
    }

    public void setProximo(NoSimples<T> proximo) {
        this.proximo = proximo;
    }
}