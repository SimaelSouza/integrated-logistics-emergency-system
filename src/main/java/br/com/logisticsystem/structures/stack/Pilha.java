package br.com.logisticsystem.structures.stack;

import br.com.logisticsystem.exceptions.EstruturaVaziaException;

public class Pilha<T> {

    private NoPilha<T> topo;
    private int tamanho;
    private final int limite;

    public Pilha(int limite) {
        if (limite <= 0) {
            throw new IllegalArgumentException("O limite da pilha deve ser maior que zero.");
        }
        this.topo = null;
        this.tamanho = 0;
        this.limite = limite;
    }

    public void push(T valor) {
        if (valor == null) {
            throw new IllegalArgumentException("Não é permitido empilhar valor nulo.");
        }
        if (isFull()) {
            throw new IllegalStateException("Pilha cheia. Limite de " + limite + " elementos atingido.");
        }

        NoPilha<T> novoNo = new NoPilha<>(valor);
        novoNo.setProximo(topo);
        topo = novoNo;
        tamanho++;
    }

    public T pop() {
        if (isEmpty()) {
            throw new EstruturaVaziaException("Não é possível remover: pilha vazia.");
        }

        T valor = topo.getValor();
        topo = topo.getProximo();
        tamanho--;
        return valor;
    }

    public T peek() {
        if (isEmpty()) {
            throw new EstruturaVaziaException("Não é possível consultar: pilha vazia.");
        }
        return topo.getValor();
    }

    public Object[] listar() {
        if (isEmpty()) {
            return new Object[0];
        }

        Object[] elementos = new Object[tamanho];
        NoPilha<T> atual = topo;

        for (int i = 0; i < tamanho; i++) {
            elementos[i] = atual.getValor();
            atual = atual.getProximo();
        }

        return elementos;
    }

    public void limpar() {
        topo = null;
        tamanho = 0;
    }

    public boolean isEmpty() {
        return tamanho == 0;
    }

    public boolean isFull() {
        return tamanho == limite;
    }

    public int getTamanho() {
        return tamanho;
    }

    public int getLimite() {
        return limite;
    }

    @Override
    public String toString() {
        return "Pilha{" +
                "topo=" + topo +
                ", tamanho=" + tamanho +
                ", limite=" + limite +
                '}';
    }
}