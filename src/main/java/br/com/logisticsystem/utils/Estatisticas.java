package br.com.logisticsystem.utils;

public class Estatisticas {

    private long comparacoes;
    private long trocas;
    private long tempo;
    private String nomeAlgoritmo;
    private int tamanhoEntrada;

    public Estatisticas(String nomeAlgoritmo, int tamanhoEntrada) {
        validarNomeAlgoritmo(nomeAlgoritmo);
        validarTamanhoEntrada(tamanhoEntrada);

        this.nomeAlgoritmo = nomeAlgoritmo;
        this.tamanhoEntrada = tamanhoEntrada;
        this.comparacoes = 0;
        this.trocas = 0;
        this.tempo = 0;
    }


    public void incrementarComparacoes() {
        comparacoes++;
    }

    public void incrementarTrocas() {
        trocas++;
    }


    public void setTempo(long tempo) {
        if (tempo < 0) {
            throw new IllegalArgumentException("O tempo não pode ser negativo.");
        }
        this.tempo = tempo;
    }

    public void setNomeAlgoritmo(String nomeAlgoritmo) {
        validarNomeAlgoritmo(nomeAlgoritmo);
        this.nomeAlgoritmo = nomeAlgoritmo;
    }

    public void setTamanhoEntrada(int tamanhoEntrada) {
        validarTamanhoEntrada(tamanhoEntrada);
        this.tamanhoEntrada = tamanhoEntrada;
    }


    public long getComparacoes() {
        return comparacoes;
    }

    public long getTrocas() {
        return trocas;
    }

    public long getTempo() {
        return tempo;
    }

    public String getNomeAlgoritmo() {
        return nomeAlgoritmo;
    }

    public int getTamanhoEntrada() {
        return tamanhoEntrada;
    }


    public void resetar() {
        this.comparacoes = 0;
        this.trocas = 0;
        this.tempo = 0;
    }


    public double getTempoMilissegundos() {
        return tempo / 1_000_000.0;
    }


    public String getResumo() {
        String tempoFormatado;

        if (tempo < 1_000_000) {
            tempoFormatado = tempo + "ns";
        } else {
            tempoFormatado = String.format("%.1fms", getTempoMilissegundos());
        }

        String resumo = nomeAlgoritmo
                + " | n=" + tamanhoEntrada
                + " | " + comparacoes + " comparações"
                + " | " + trocas + " trocas"
                + " | " + tempoFormatado;

        return resumo;
    }

    @Override
    public String toString() {
        return getResumo();
    }


    private void validarNomeAlgoritmo(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do algoritmo é obrigatório.");
        }
    }

    private void validarTamanhoEntrada(int tamanho) {
        if (tamanho < 0) {
            throw new IllegalArgumentException("O tamanho da entrada não pode ser negativo.");
        }
    }
}