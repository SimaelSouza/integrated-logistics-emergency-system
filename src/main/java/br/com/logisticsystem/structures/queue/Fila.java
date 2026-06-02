package br.com.logisticsystem.structures.queue;

import br.com.logisticsystem.models.Atendimento;

public class Fila {
    private int capacidade;
    private Atendimento[] atendimentos;
    public Fila(int capacidade) {
        this.capacidade=capacidade;
        this.atendimentos = new Atendimento[capacidade];
    }
    public void visualizaFila(){
        for (int i =0 ; i<capacidade; i++){
            if(atendimentos[i] != null){
            System.out.println(atendimentos[i]);
            } else {continue;}
        }
    }
    public void remocao(){
        for (int i =0; i <capacidade; i ++){
            if(atendimentos[i] != null){
                atendimentos[i]=null;
                break;
            }
        }
    }




}
