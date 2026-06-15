package br.com.logisticsystem.structures.doublylinkedlist;

import br.com.logisticsystem.exceptions.PosicaoInvalidaException;

public class ListaDupla<T> {

    private NoDuplo<T> primeiro;
    private NoDuplo<T> ultimo;
    private int tamanho;

    public ListaDupla() {
        this.primeiro = null;
        this.ultimo = null;
        this.tamanho = 0;
    }

    public void inserir(T elemento) {

    }

    public T remover(int posicao) {
        return null;
    }

    public T buscar(int posicao) {
        return null;
    }

    public String listarFrente() {
        return "";
    }

    public String listarTras() {
        return "";
    }

    public boolean isEmpty() {
        return tamanho == 0;
    }

    public int getTamanho() {
        return tamanho;
    }

    private void validarPosicao(int posicao) throws PosicaoInvalidaException {

    }

}
