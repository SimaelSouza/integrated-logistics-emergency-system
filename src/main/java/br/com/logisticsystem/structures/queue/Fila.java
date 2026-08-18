package br.com.logisticsystem.structures.queue;

import br.com.logisticsystem.models.Atendimento;

public class Fila {

    private Atendimento[] atendimentos;
    private int fim;

    public Fila(int capacidade) {
        atendimentos = new Atendimento[capacidade];
        fim = 0;
    }


    public void enqueue(Atendimento atendimento) {
        if(isFull()){
            throw new IllegalStateException("Fila Cheia!!");
        }

        for(int i=0; i<atendimentos.length; i++){
            if(atendimentos[i] == null){
                atendimentos[i] = atendimento;
                fim++;
                break;
            }
        }
    }

    public Atendimento dequeue() {
        if(isEmpty()){
            System.out.println("A fila está vazia!");
            return null;
        }

        for (int i = 0; i < atendimentos.length; i++) {
            if(atendimentos[i] != null){
                Atendimento atendimento = atendimentos[i];
                atendimentos[i] = null;
                fim--;
                reorganizar();
                return atendimento;
            }
        }
        return null;
    }

    public Atendimento peek() {
        if(isEmpty()){
            System.out.println("A fila está vazia!");
            return null;
        }

        for (int i = 0; i < atendimentos.length; i++) {
            if(atendimentos[i] != null){
                return atendimentos[i];
            }
        }
        return null;
    }

    public Atendimento[] listar() {
        System.out.println("Listando Fila simples de atendimentos");
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

    private void reorganizar() {
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




