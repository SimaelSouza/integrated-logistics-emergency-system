package br.com.logisticsystem.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Relatorio {

    private final UUID id;
    private final LocalDateTime horarioGeracao;

    private int totalAtendimentosPendentes;
    private double tempoMedioEsperaSegundos;

    private int totalPedidos;
    private int pedidosAtivos;

    private int totalEntregas;
    private int entregasConcluidas;
    private int entregasCanceladas;
    private double tempoMedioEntregaHoras;

    private int quantidadeMovimentacoes;

    private double tempoMedioProcessamentoMs;
    private String estruturaMaisEficiente;
    private String algoritmoMaisRapido;
    private long quantidadeOperacoes;
    private double tamanhoMedioFilas;
    private double taxaCrescimentoSistema;

    public Relatorio() {
        this.id = UUID.randomUUID();
        this.horarioGeracao = LocalDateTime.now();
    }


    public UUID getId() {
        return id;
    }

    public LocalDateTime getHorarioGeracao() {
        return horarioGeracao;
    }

    public int getTotalAtendimentosPendentes() {
        return totalAtendimentosPendentes;
    }

    public double getTempoMedioEsperaSegundos() {
        return tempoMedioEsperaSegundos;
    }

    public int getTotalPedidos() {
        return totalPedidos;
    }

    public int getPedidosAtivos() {
        return pedidosAtivos;
    }

    public int getTotalEntregas() {
        return totalEntregas;
    }

    public int getEntregasConcluidas() {
        return entregasConcluidas;
    }

    public int getEntregasCanceladas() {
        return entregasCanceladas;
    }

    public double getTempoMedioEntregaHoras() {
        return tempoMedioEntregaHoras;
    }

    public int getQuantidadeMovimentacoes() {
        return quantidadeMovimentacoes;
    }

    public double getTempoMedioProcessamentoMs() {
        return tempoMedioProcessamentoMs;
    }

    public String getEstruturaMaisEficiente() {
        return estruturaMaisEficiente;
    }

    public String getAlgoritmoMaisRapido() {
        return algoritmoMaisRapido;
    }

    public long getQuantidadeOperacoes() {
        return quantidadeOperacoes;
    }

    public double getTamanhoMedioFilas() {
        return tamanhoMedioFilas;
    }

    public double getTaxaCrescimentoSistema() {
        return taxaCrescimentoSistema;
    }

    public void setTotalAtendimentosPendentes(int valor) {
        this.totalAtendimentosPendentes = valor;
    }

    public void setTempoMedioEsperaSegundos(double valor) {
        this.tempoMedioEsperaSegundos = valor;
    }

    public void setTotalPedidos(int valor) {
        this.totalPedidos = valor;
    }

    public void setPedidosAtivos(int valor) {
        this.pedidosAtivos = valor;
    }

    public void setTotalEntregas(int valor) {
        this.totalEntregas = valor;
    }

    public void setEntregasConcluidas(int valor) {
        this.entregasConcluidas = valor;
    }

    public void setEntregasCanceladas(int valor) {
        this.entregasCanceladas = valor;
    }

    public void setTempoMedioEntregaHoras(double valor) {
        this.tempoMedioEntregaHoras = valor;
    }

    public void setQuantidadeMovimentacoes(int valor) {
        this.quantidadeMovimentacoes = valor;
    }

    public void setTempoMedioProcessamentoMs(double valor) {
        this.tempoMedioProcessamentoMs = valor;
    }

    public void setEstruturaMaisEficiente(String valor) {
        this.estruturaMaisEficiente = valor;
    }

    public void setAlgoritmoMaisRapido(String valor) {
        this.algoritmoMaisRapido = valor;
    }

    public void setQuantidadeOperacoes(long valor) {
        this.quantidadeOperacoes = valor;
    }

    public void setTamanhoMedioFilas(double valor) {
        this.tamanhoMedioFilas = valor;
    }

    public void setTaxaCrescimentoSistema(double valor) {
        this.taxaCrescimentoSistema = valor;
    }

    @Override
    public String toString() {
        return "Relatorio{" +
                "id=" + id +
                ", horarioGeracao=" + horarioGeracao +
                ", totalAtendimentosPendentes=" + totalAtendimentosPendentes +
                ", tempoMedioEsperaSegundos=" + tempoMedioEsperaSegundos +
                ", totalPedidos=" + totalPedidos +
                ", pedidosAtivos=" + pedidosAtivos +
                ", totalEntregas=" + totalEntregas +
                ", entregasConcluidas=" + entregasConcluidas +
                ", entregasCanceladas=" + entregasCanceladas +
                ", tempoMedioEntregaHoras=" + tempoMedioEntregaHoras +
                ", quantidadeMovimentacoes=" + quantidadeMovimentacoes +
                ", tempoMedioProcessamentoMs=" + tempoMedioProcessamentoMs +
                ", estruturaMaisEficiente='" + estruturaMaisEficiente + '\'' +
                ", algoritmoMaisRapido='" + algoritmoMaisRapido + '\'' +
                ", quantidadeOperacoes=" + quantidadeOperacoes +
                ", tamanhoMedioFilas=" + tamanhoMedioFilas +
                ", taxaCrescimentoSistema=" + taxaCrescimentoSistema +
                '}';
    }
}