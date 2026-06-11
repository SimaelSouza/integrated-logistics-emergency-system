package br.com.logisticsystem.structures.queue;

import br.com.logisticsystem.models.Atendimento;
import br.com.logisticsystem.models.EnumPrioridade;

public class FilaPrioritaria {
    private Atendimento[] atendimentos;
    private int fim;
    public FilaPrioritaria(int capacidade) {
        this.atendimentos = new Atendimento[capacidade];
        this.fim = 0;
    }

    public void enqueue(Atendimento atendimento) {
        if (isFull()) {
            throw new IllegalStateException("Fila Cheia!!");
        }
        if (atendimento.getPrioridade() == EnumPrioridade.SEM_PRIORIDADE) {
            throw new IllegalArgumentException("A fila prioritária não aceita atendimentos SEM_PRIORIDADE!");
        }
        atendimentos[fim] = atendimento;
        fim++;
        int i = fim - 1;

        while (i > 0 && atendimentos[i].getPrioridade().getNivel() > atendimentos[i - 1].getPrioridade().getNivel()) {
            Atendimento temp = atendimentos[i];
            atendimentos[i] = atendimentos[i - 1];
            atendimentos[i - 1] = temp;
            i--;
        }
    }

    public Atendimento dequeue() {
        if (isEmpty()) {
            System.out.println("A fila está vazia!");
            return null;
        }

        Atendimento atendimento = atendimentos[0];
        atendimentos[0] = null;
        fim--;

        reorganizar();
        return atendimento;
    }

    public Atendimento peek() {
        if (isEmpty()) {
            System.out.println("A fila está vazia!");
            return null;
        }
        return atendimentos[0];
    }

    public Atendimento[] listar() {
        System.out.println("Listando Fila Prioritária de atendimentos");
        Atendimento[] elementosAtuais = new Atendimento[fim];
        for (int i = 0; i < fim; i++) {
            System.out.println("Atendimento " + i + ": " + atendimentos[i]);
            elementosAtuais[i] = atendimentos[i];
        }
        return elementosAtuais;
    }

    public boolean isEmpty() {
        return fim == 0;
    }

    public boolean isFull() {
        return fim == atendimentos.length;
    }

    public int getTamanho() {
        return fim;
    }

    public void reorganizar() {
        int destino = 0;
        for (int origem = 0; origem < atendimentos.length; origem++) {
            if (atendimentos[origem] != null) {
                atendimentos[destino] = atendimentos[origem];

                if (origem != destino) {
                    atendimentos[origem] = null;
                }
                destino++;
            }
        }
    }

}
