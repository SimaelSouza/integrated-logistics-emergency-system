package br.com.logisticsystem.structures.singlylinkedlist;

public class ListaSimples<T> {
    private NoSimples<T> inicio;
    private int tamanho;

    public ListaSimples() {
        this.inicio = null;
        this.tamanho = 0;
    }

    public int getTamanho() {
        return this.tamanho;
    }

    public boolean estaVazia() {
        return this.tamanho == 0;
    }

    private void validarPosicao(int posicao) {
        if (posicao < 0 || posicao >= this.tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida: " + posicao + ". Tamanho atual: " + this.tamanho);
        }
    }

    private void validarPosicaoInsercao(int posicao) {
        if (posicao < 0 || posicao > this.tamanho) {
            throw new IndexOutOfBoundsException("Posição de inserção inválida: " + posicao + ". Tamanho atual: " + this.tamanho);
        }
    }

    public void inserirNoInicio(T elemento) {
        NoSimples<T> novoNo = new NoSimples<>(elemento);
        novoNo.setProximo(this.inicio);
        this.inicio = novoNo;
        this.tamanho++;
    }

    public void inserirNoFim(T elemento) {
        if (estaVazia()) {
            inserirNoInicio(elemento);
            return;
        }

        NoSimples<T> novoNo = new NoSimples<>(elemento);
        NoSimples<T> atual = this.inicio;

        while (atual.getProximo() != null) {
            atual = atual.getProximo();
        }

        atual.setProximo(novoNo);
        this.tamanho++;
    }

    public void inserir(int posicao, T elemento) {
        validarPosicaoInsercao(posicao);

        if (posicao == 0) {
            inserirNoInicio(elemento);
        } else if (posicao == this.tamanho) {
            inserirNoFim(elemento);
        } else {
            NoSimples<T> atual = this.inicio;
            for (int i = 0; i < posicao - 1; i++) {
                atual = atual.getProximo();
            }

            NoSimples<T> novoNo = new NoSimples<>(elemento, atual.getProximo());
            atual.setProximo(novoNo);
            this.tamanho++;
        }
    }

    public T buscar(int posicao) {
        validarPosicao(posicao);

        NoSimples<T> atual = this.inicio;
        for (int i = 0; i < posicao; i++) {
            atual = atual.getProximo();
        }

        return atual.getElemento();
    }

    public void atualizar(int posicao, T novoElemento) {
        validarPosicao(posicao);

        NoSimples<T> atual = this.inicio;
        for (int i = 0; i < posicao; i++) {
            atual = atual.getProximo();
        }

        atual.setElemento(novoElemento);
    }

    public T remover(int posicao) {
        validarPosicao(posicao);

        T elementoRemovido;

        if (posicao == 0) {
            elementoRemovido = this.inicio.getElemento();
            this.inicio = this.inicio.getProximo();
        } else {
            NoSimples<T> anterior = this.inicio;
            for (int i = 0; i < posicao - 1; i++) {
                anterior = anterior.getProximo();
            }

            NoSimples<T> alvo = anterior.getProximo();
            elementoRemovido = alvo.getElemento();
            anterior.setProximo(alvo.getProximo());
        }

        this.tamanho--;
        return elementoRemovido;
    }

    public void listar() {
        if (estaVazia()) {
            System.out.println("[]");
            return;
        }

        StringBuilder sb = new StringBuilder("[");
        NoSimples<T> atual = this.inicio;

        while (atual != null) {
            sb.append(atual.getElemento());
            if (atual.getProximo() != null) {
                sb.append(", ");
            }
            atual = atual.getProximo();
        }

        sb.append("]");
        System.out.println(sb.toString());
    }
}
