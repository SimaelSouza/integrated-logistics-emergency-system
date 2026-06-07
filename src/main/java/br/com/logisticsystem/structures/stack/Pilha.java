package br.com.logisticsystem.structures.stack;

public class Pilha {
	public class Fila {
    private int capacidade;
    private int tamanho; 
    private Atendimento[] atendimentos;

    public Fila(int capacidade) {
        this.capacidade = capacidade;
        this.tamanho = 0;
        this.atendimentos = new Atendimento[capacidade];
    }

    public boolean inserir(Atendimento atendimento) {
        if (tamanho < capacidade) {
            atendimentos[tamanho] = atendimento;
            tamanho++;
            return true;
        }
        System.out.println("Fila cheia!");
        return false;
    }

    public void visualizaFila() {
        if (tamanho == 0) {
            System.out.println("Fila vazia.");
            return;
        }
        for (int i = 0; i < tamanho; i++) {
            System.out.println(atendimentos[i]);
        }
    }

    public void remocao() {
        if (tamanho == 0) {
            System.out.println("Fila já está vazia.");
            return;
        }
        
        atendimentos[0] = null; 
        reorganizarFila();      
        tamanho--;
    }

    public void reorganizarFila() {
        for (int i = 0; i < tamanho - 1; i++) {
            atendimentos[i] = atendimentos[i + 1];
        }
        atendimentos[tamanho - 1] = null; 
    }

    
    public void remocaoPorAtendimento(Atendimento A) {
        for (int i = 0; i < tamanho; i++) {
            
            if (atendimentos[i] != null && atendimentos[i].equals(A)) {
                
                for (int j = i; j < tamanho - 1; j++) {
                    atendimentos[j] = atendimentos[j + 1];
                }
                atendimentos[tamanho - 1] = null;
                tamanho--;
                break;
            }
        }
    }

    public void medirTempoDeExecucaoDaImpressao() {
        long inicio = System.nanoTime();
        visualizaFila();
        long fim = System.nanoTime();
        long tempoDecorrido = fim - inicio;

        System.out.println("Tempo de execução da impressão: " + tempoDecorrido + " nanossegundos");
    }
}
}
