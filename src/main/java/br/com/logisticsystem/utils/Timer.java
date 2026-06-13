package br.com.logisticsystem.utils;

public class Timer {

    private long tempoInicio;
    private long tempoFim;

    public Timer() {
        this.tempoInicio = 0;
        this.tempoFim = 0;
    }

    public void iniciar() {
        this.tempoInicio = System.nanoTime();
        this.tempoFim = 0;
    }

    public void finalizar() {
        if (tempoInicio == 0) {
            throw new IllegalStateException("O timer não foi iniciado. Chame iniciar() antes de finalizar().");
        }
        this.tempoFim = System.nanoTime();
    }


    public void resetar() {
        this.tempoInicio = 0;
        this.tempoFim = 0;
    }


    public long getTempoNanosegundos() {
        validarMedicaoConcluida();
        return tempoFim - tempoInicio;
    }

    public double getTempoMilissegundos() {
        validarMedicaoConcluida();
        return getTempoNanosegundos() / 1_000_000.0;
    }


    public String getTempoFormatado() {
        validarMedicaoConcluida();

        long nano = getTempoNanosegundos();

        if (nano < 1_000_000) {
            return nano + " ns";
        }

        return String.format("%.1f ms", getTempoMilissegundos());
    }


    private void validarMedicaoConcluida() {
        if (tempoInicio == 0) {
            throw new IllegalStateException("O timer não foi iniciado.");
        }
        if (tempoFim == 0) {
            throw new IllegalStateException("O timer não foi finalizado. Chame finalizar() antes de consultar.");
        }
    }

    @Override
    public String toString() {
        if (tempoInicio == 0) {
            return "Timer[não iniciado]";
        }
        if (tempoFim == 0) {
            return "Timer[em execução]";
        }
        return "Timer[" + getTempoFormatado() + "]";
    }
}
