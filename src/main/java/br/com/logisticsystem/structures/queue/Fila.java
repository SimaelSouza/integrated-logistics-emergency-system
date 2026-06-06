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
    public void reorganizarFila(){
        for (int i =0; i <capacidade; i ++){
            if(atendimentos[i] == null && atendimentos[i+1] != null){
                atendimentos[i]=atendimentos[i+1];
            }
        }
    }
    public void remocaoPorAtendimento(Atendimento A) {
        for (int i = 0; i < capacidade; i++) {
            if (atendimentos[i] == A) {
                atendimentos[i] = null;
                break;
            }
        }
    }
    public void tempoMedioEspera(){
        long inicio = System.nanoTime();
        visualizaFila();
        long fim = System.nanoTime();
        long tempoDecorrido = fim - inicio;

        System.out.println("Tempo de execução: " + tempoDecorrido + " nanossegundos");
        }

}




