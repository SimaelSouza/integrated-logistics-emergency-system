package br.com.logisticsystem.structures.singlylinkedlist;

import br.com.logisticsystem.exceptions.PosicaoInvalidaException;

public class ListaSimples<T> {

    private NoSimples<T> primeiro;
    private int tamanho;

    public ListaSimples() {
        this.primeiro = null;
        this.tamanho = 0;
    }

    public void inserirInicio(T elemento) {

    }

    public void inserirFim(T elemento) {

    }

    public void inserir(int posicao, T elemento) {

    }

    public T remover(int posicao) {
        return null;
    }

    public T buscar(int posicao) {
        return null;
    }

    public void atualizar(int posicao, T elemento) {

    }

    public String listar() {
        return "";
    }

    public int getTamanho() {
        return tamanho;
    }

    public boolean isEmpty() {
        return tamanho == 0;
    }

    private void validarPosicao(int posicao) throws PosicaoInvalidaException {

    }

    private void validarPosicaoInsercao(int posicao) throws PosicaoInvalidaException {

    }
}
