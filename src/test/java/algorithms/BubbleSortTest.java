package algorithms;

import br.com.logisticsystem.algorithms.quadratic.BubbleSort;
import br.com.logisticsystem.utils.Estatisticas;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortTest {

    @Test
    void deveOrdenarArrayAleatorio() {

        int[] array = {5, 2, 8, 1, 3};

        Estatisticas estatisticas =
                new Estatisticas("BubbleSort", array.length);

        BubbleSort.ordenar(array, estatisticas);

        assertArrayEquals(
                new int[]{1, 2, 3, 5, 8},
                array
        );
    }

    @Test
    void deveOrdenarArrayInvertido() {

        int[] array = {5, 4, 3, 2, 1};

        Estatisticas estatisticas =
                new Estatisticas("BubbleSort", array.length);

        BubbleSort.ordenar(array, estatisticas);

        assertArrayEquals(
                new int[]{1, 2, 3, 4, 5},
                array
        );
    }

    @Test
    void deveManterArrayOrdenado() {

        int[] array = {1, 2, 3, 4, 5};

        Estatisticas estatisticas =
                new Estatisticas("BubbleSort", array.length);

        BubbleSort.ordenar(array, estatisticas);

        assertArrayEquals(
                new int[]{1, 2, 3, 4, 5},
                array
        );
    }

    @Test
    void deveOrdenarArrayComElementosRepetidos() {

        int[] array = {4, 2, 4, 1, 2};

        Estatisticas estatisticas =
                new Estatisticas("BubbleSort", array.length);

        BubbleSort.ordenar(array, estatisticas);

        assertArrayEquals(
                new int[]{1, 2, 2, 4, 4},
                array
        );
    }

    @Test
    void deveOrdenarArrayComUmElemento() {

        int[] array = {10};

        Estatisticas estatisticas =
                new Estatisticas("BubbleSort", array.length);

        BubbleSort.ordenar(array, estatisticas);

        assertArrayEquals(
                new int[]{10},
                array
        );
    }

    @Test
    void deveOrdenarArrayVazio() {

        int[] array = {};

        Estatisticas estatisticas =
                new Estatisticas("BubbleSort", array.length);

        BubbleSort.ordenar(array, estatisticas);

        assertArrayEquals(
                new int[]{},
                array
        );
    }

    @Test
    void deveRegistrarComparacoes() {

        int[] array = {3, 2, 1};

        Estatisticas estatisticas =
                new Estatisticas("BubbleSort", array.length);

        BubbleSort.ordenar(array, estatisticas);

        assertTrue(
                estatisticas.getComparacoes() > 0
        );
    }

    @Test
    void deveRegistrarTrocas() {

        int[] array = {3, 2, 1};

        Estatisticas estatisticas =
                new Estatisticas("BubbleSort", array.length);

        BubbleSort.ordenar(array, estatisticas);

        assertTrue(
                estatisticas.getTrocas() > 0
        );
    }

    @Test
    void naoDeveRealizarTrocasEmArrayOrdenado() {

        int[] array = {1, 2, 3, 4, 5};

        Estatisticas estatisticas =
                new Estatisticas("BubbleSort", array.length);

        BubbleSort.ordenar(array, estatisticas);

        assertEquals(
                0,
                estatisticas.getTrocas()
        );
    }
}