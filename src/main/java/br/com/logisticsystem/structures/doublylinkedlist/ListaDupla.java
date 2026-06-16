package br.com.logisticsystem.structures.doublylinkedlist;

public class ListaDupla<T> {
    private NoDuplo<T> inicio;
    private NoDuplo<T> fim;
    private int tamanho;

    public ListaDupla() {
        this.inicio = null;
        this.fim = null;
        this.tamanho = 0;
    }


    public int getTamanho() {
        return this.tamanho;
    }


    public boolean isEmpty() {
        return this.tamanho == 0;
    }

    private void validarPosicao(int posicao) {
        if (posicao < 0 || posicao >= this.tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida: " + posicao);
        }
    }

    private void validarPosicaoInsercao(int posicao) {
        if (posicao < 0 || posicao > this.tamanho) {
            throw new IndexOutOfBoundsException("Posição de inserção inválida: " + posicao);
        }
    }


    private NoDuplo<T> obterNo(int posicao) {
        validarPosicao(posicao);

        NoDuplo<T> atual;
        if (posicao < this.tamanho / 2) {
            atual = this.inicio;
            for (int i = 0; i < posicao; i++) {
                atual = atual.getProximo();
            }
        } else {
            atual = this.fim;
            for (int i = this.tamanho - 1; i > posicao; i--) {
                atual = atual.getAnterior();
            }
        }
        return atual;
    }


    public T buscar(int posicao) {
        return obterNo(posicao).getElemento();
    }


    public void inserir(T elemento) {
        NoDuplo<T> novoNo = new NoDuplo<>(elemento);
        if (isEmpty()) {
            this.inicio = novoNo;
            this.fim = novoNo;
        } else {
            this.fim.setProximo(novoNo);
            novoNo.setAnterior(this.fim);
            this.fim = novoNo;
        }
        this.tamanho++;
    }


    public void inserir(int posicao, T elemento) {
        validarPosicaoInsercao(posicao);

        if (posicao == 0) {
            NoDuplo<T> novoNo = new NoDuplo<>(elemento);
            if (isEmpty()) {
                this.inicio = novoNo;
                this.fim = novoNo;
            } else {
                novoNo.setProximo(this.inicio);
                this.inicio.setAnterior(novoNo);
                this.inicio = novoNo;
            }
            this.tamanho++;
        } else if (posicao == this.tamanho) {
            inserir(elemento);
        } else {
            NoDuplo<T> noAtual = obterNo(posicao);
            NoDuplo<T> noAnterior = noAtual.getAnterior();
            NoDuplo<T> novoNo = new NoDuplo<>(elemento, noAtual, noAnterior);

            noAnterior.setProximo(novoNo);
            noAtual.setAnterior(novoNo);
            this.tamanho++;
        }
    }

    public T remover(int posicao) {
        validarPosicao(posicao);

        NoDuplo<T> noAlvo = obterNo(posicao);
        T elementoRemovido = noAlvo.getElemento();

        if (posicao == 0) {
            this.inicio = noAlvo.getProximo();
            if (this.inicio != null) {
                this.inicio.setAnterior(null);
            } else {
                this.fim = null;
            }
        } else if (posicao == this.tamanho - 1) {
            this.fim = noAlvo.getAnterior();
            if (this.fim != null) {
                this.fim.setProximo(null);
            } else {
                this.inicio = null;
            }
        } else {
            noAlvo.getAnterior().setProximo(noAlvo.getProximo());
            noAlvo.getProximo().setAnterior(noAlvo.getAnterior());
        }

        this.tamanho--;
        return elementoRemovido;
    }


    public String listarFrente() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        NoDuplo<T> atual = this.inicio;
        while (atual != null) {
            sb.append(atual.getElemento().toString());
            if (atual.getProximo() != null) {
                sb.append(", ");
            }
            atual = atual.getProximo();
        }
        sb.append("]");
        return sb.toString();
    }


    public String listarTras() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        NoDuplo<T> atual = this.fim;
        while (atual != null) {
            sb.append(atual.getElemento().toString());
            if (atual.getAnterior() != null) {
                sb.append(", ");
            }
            atual = atual.getAnterior();
        }
        sb.append("]");
        return sb.toString();
    }
}