package algorithms;

import br.com.logisticsystem.algorithms.quadratic.BubbleSort;
import br.com.logisticsystem.models.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortTest {

    @Test
    void deveOrdenarArrayDeInteiros() {

        int[] numeros = {5, 2, 8, 1, 3};

        BubbleSort.ordenarInteiros(numeros);

        assertArrayEquals(
                new int[]{1, 2, 3, 5, 8},
                numeros
        );
    }

    @Test
    void deveManterArrayJaOrdenado() {

        int[] numeros = {1, 2, 3, 4, 5};

        BubbleSort.ordenarInteiros(numeros);

        assertArrayEquals(
                new int[]{1, 2, 3, 4, 5},
                numeros
        );
    }

    @Test
    void deveOrdenarArrayInvertido() {

        int[] numeros = {5, 4, 3, 2, 1};

        BubbleSort.ordenarInteiros(numeros);

        assertArrayEquals(
                new int[]{1, 2, 3, 4, 5},
                numeros
        );
    }

    // ─────────────────────────────────────────────
    // Prioridade
    // ─────────────────────────────────────────────

    @Test
    void deveOrdenarPedidosPorPrioridade() {

        Pedido baixa =
                new Pedido("Cliente A", "Carga", EnumPrioridade.BAIXA);

        Pedido alta =
                new Pedido("Cliente B", "Carga", EnumPrioridade.ALTA);

        Pedido critica =
                new Pedido("Cliente C", "Carga", EnumPrioridade.CRITICA);

        Pedido media =
                new Pedido("Cliente D", "Carga", EnumPrioridade.MEDIA);

        Pedido[] pedidos = {
                alta,
                baixa,
                critica,
                media
        };

        BubbleSort.ordenarPrioridadePedidos(pedidos);

        assertEquals(EnumPrioridade.BAIXA, pedidos[0].getPrioridade());
        assertEquals(EnumPrioridade.MEDIA, pedidos[1].getPrioridade());
        assertEquals(EnumPrioridade.ALTA, pedidos[2].getPrioridade());
        assertEquals(EnumPrioridade.CRITICA, pedidos[3].getPrioridade());
    }

    // ─────────────────────────────────────────────
    // Horário
    // ─────────────────────────────────────────────

    @Test
    void deveOrdenarAtendimentosPorHorario() throws InterruptedException {

        Atendimento a1 =
                new Atendimento(
                        "Cliente A",
                        EnumPrioridade.MEDIA,
                        EnumTipo.ACIDENTE
                );

        Thread.sleep(5);

        Atendimento a2 =
                new Atendimento(
                        "Cliente B",
                        EnumPrioridade.MEDIA,
                        EnumTipo.ACIDENTE
                );

        Thread.sleep(5);

        Atendimento a3 =
                new Atendimento(
                        "Cliente C",
                        EnumPrioridade.MEDIA,
                        EnumTipo.ACIDENTE
                );

        Atendimento[] atendimentos = {
                a3,
                a1,
                a2
        };

        BubbleSort.ordenarHorarioAtendimentos(atendimentos);

        assertEquals(a1, atendimentos[0]);
        assertEquals(a2, atendimentos[1]);
        assertEquals(a3, atendimentos[2]);
    }

    // ─────────────────────────────────────────────
    // Status
    // ─────────────────────────────────────────────

    @Test
    void deveOrdenarEntregasPorStatus() {

        Pedido pedido =
                new Pedido(
                        "Cliente",
                        "Carga",
                        EnumPrioridade.ALTA
                );

        Rota rota =
                new Rota(
                        "Santa Maria",
                        "Porto Alegre",
                        ""
                );

        Entrega entregue =
                new Entrega(
                        pedido,
                        rota,
                        EnumPrioridade.ALTA,
                        EnumStatus.ENTREGUE
                );

        Entrega pendente =
                new Entrega(
                        pedido,
                        rota,
                        EnumPrioridade.ALTA,
                        EnumStatus.PENDENTE
                );

        Entrega transito =
                new Entrega(
                        pedido,
                        rota,
                        EnumPrioridade.ALTA,
                        EnumStatus.EM_TRANSITO
                );

        Entrega[] entregas = {
                entregue,
                transito,
                pendente
        };

        BubbleSort.ordenarStatusEntregas(entregas);

        assertEquals(EnumStatus.PENDENTE,
                entregas[0].getStatus());

        assertEquals(EnumStatus.EM_TRANSITO,
                entregas[1].getStatus());

        assertEquals(EnumStatus.ENTREGUE,
                entregas[2].getStatus());
    }

    // ─────────────────────────────────────────────
    // Casos limite
    // ─────────────────────────────────────────────

    @Test
    void deveOrdenarArrayVazio() {

        int[] numeros = {};

        BubbleSort.ordenarInteiros(numeros);

        assertEquals(0, numeros.length);
    }

    @Test
    void deveOrdenarArrayComUmElemento() {

        int[] numeros = {10};

        BubbleSort.ordenarInteiros(numeros);

        assertArrayEquals(
                new int[]{10},
                numeros
        );
    }
}