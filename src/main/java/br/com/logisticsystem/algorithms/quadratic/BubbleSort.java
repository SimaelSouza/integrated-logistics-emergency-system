package br.com.logisticsystem.algorithms.quadratic;

import br.com.logisticsystem.models.Atendimento;
import br.com.logisticsystem.models.Entrega;
import br.com.logisticsystem.models.Pedido;
import br.com.logisticsystem.models.Rota;

public class BubbleSort {

    public static void ordenarInteiros(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int aux = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = aux;
                }
            }
        }
    }

    public static void ordenarPrioridadeAtendimentos(Atendimento[] atendimentos) {
        for (int i = 0; i < atendimentos.length - 1; i++) {
            for (int j = 0; j < atendimentos.length - 1 - i; j++) {

                if (atendimentos[j].getPrioridade().getNivel() >
                        atendimentos[j + 1].getPrioridade().getNivel()) {

                    Atendimento aux = atendimentos[j];
                    atendimentos[j] = atendimentos[j + 1];
                    atendimentos[j + 1] = aux;
                }
            }
        }
    }

    public static void ordenarPrioridadePedidos(Pedido[] pedidos) {
        for (int i = 0; i < pedidos.length - 1; i++) {
            for (int j = 0; j < pedidos.length - 1 - i; j++) {

                if (pedidos[j].getPrioridade().getNivel() >
                        pedidos[j + 1].getPrioridade().getNivel()) {

                    Pedido aux = pedidos[j];
                    pedidos[j] = pedidos[j + 1];
                    pedidos[j + 1] = aux;
                }
            }
        }
    }

    public static void ordenarPrioridadeEntregas(Entrega[] entregas) {
        for (int i = 0; i < entregas.length - 1; i++) {
            for (int j = 0; j < entregas.length - 1 - i; j++) {

                if (entregas[j].getPrioridade().getNivel() >
                        entregas[j + 1].getPrioridade().getNivel()) {

                    Entrega aux = entregas[j];
                    entregas[j] = entregas[j + 1];
                    entregas[j + 1] = aux;
                }
            }
        }
    }

    public static void ordenarHorarioAtendimentos(Atendimento[] atendimentos) {
        for (int i = 0; i < atendimentos.length - 1; i++) {
            for (int j = 0; j < atendimentos.length - 1 - i; j++) {

                if (atendimentos[j].getHorarioSolicitacao()
                        .isAfter(atendimentos[j + 1].getHorarioSolicitacao())) {

                    Atendimento aux = atendimentos[j];
                    atendimentos[j] = atendimentos[j + 1];
                    atendimentos[j + 1] = aux;
                }
            }
        }
    }

    public static void ordenarHorarioPedidos(Pedido[] pedidos) {
        for (int i = 0; i < pedidos.length - 1; i++) {
            for (int j = 0; j < pedidos.length - 1 - i; j++) {

                if (pedidos[j].getHorarioCriacao()
                        .isAfter(pedidos[j + 1].getHorarioCriacao())) {

                    Pedido aux = pedidos[j];
                    pedidos[j] = pedidos[j + 1];
                    pedidos[j + 1] = aux;
                }
            }
        }
    }

    public static void ordenarStatusEntregas(Entrega[] entregas) {
        for (int i = 0; i < entregas.length - 1; i++) {
            for (int j = 0; j < entregas.length - 1 - i; j++) {

                if (entregas[j].getStatus().ordinal() >
                        entregas[j + 1].getStatus().ordinal()) {

                    Entrega aux = entregas[j];
                    entregas[j] = entregas[j + 1];
                    entregas[j + 1] = aux;
                }
            }
        }
    }

    public static void ordenarStatusPedidos(Pedido[] pedidos) {
        for (int i = 0; i < pedidos.length - 1; i++) {
            for (int j = 0; j < pedidos.length - 1 - i; j++) {

                if (pedidos[j].getStatus().ordinal() >
                        pedidos[j + 1].getStatus().ordinal()) {

                    Pedido aux = pedidos[j];
                    pedidos[j] = pedidos[j + 1];
                    pedidos[j + 1] = aux;
                }
            }
        }
    }
}
