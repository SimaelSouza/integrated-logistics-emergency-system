package br.com.logisticsystem.utils;

import br.com.logisticsystem.models.Atendimento;
import br.com.logisticsystem.models.Entrega;
import br.com.logisticsystem.models.Pedido;
import br.com.logisticsystem.models.Rota;

public class Comparador {

    private Comparador() {
        throw new UnsupportedOperationException("Comparador é uma classe utilitária e não deve ser instanciada.");
    }

    public static int compararPorPrioridade(Atendimento a, Atendimento b) {
        validarNaoNulo(a, "Atendimento a");
        validarNaoNulo(b, "Atendimento b");
        return Integer.compare(
                a.getPrioridade().getNivel(),
                b.getPrioridade().getNivel()
        );
    }

    public static int compararPorHorario(Atendimento a, Atendimento b) {
        validarNaoNulo(a, "Atendimento a");
        validarNaoNulo(b, "Atendimento b");
        return a.getHorarioSolicitacao().compareTo(b.getHorarioSolicitacao());
    }


    public static int compararPorPrioridade(Pedido a, Pedido b) {
        validarNaoNulo(a, "Pedido a");
        validarNaoNulo(b, "Pedido b");
        return Integer.compare(
                a.getPrioridade().getNivel(),
                b.getPrioridade().getNivel()
        );
    }

    public static int compararPorHorario(Pedido a, Pedido b) {
        validarNaoNulo(a, "Pedido a");
        validarNaoNulo(b, "Pedido b");
        return a.getHorarioCriacao().compareTo(b.getHorarioCriacao());
    }


    public static int compararPorPrioridade(Entrega a, Entrega b) {
        validarNaoNulo(a, "Entrega a");
        validarNaoNulo(b, "Entrega b");
        return Integer.compare(
                a.getPrioridade().getNivel(),
                b.getPrioridade().getNivel()
        );
    }

    public static int compararPorStatus(Entrega a, Entrega b) {
        validarNaoNulo(a, "Entrega a");
        validarNaoNulo(b, "Entrega b");
        return Integer.compare(
                a.getStatus().ordinal(),
                b.getStatus().ordinal()
        );
    }


    public static int compararPorDistancia(Rota a, Rota b) {
        validarNaoNulo(a, "Rota a");
        validarNaoNulo(b, "Rota b");
        return Integer.compare(a.getDistanciaEstimadaMt(), b.getDistanciaEstimadaMt());
    }


    private static void validarNaoNulo(Object objeto, String nome) {
        if (objeto == null) {
            throw new IllegalArgumentException(nome + " não pode ser nulo.");
        }
    }
}