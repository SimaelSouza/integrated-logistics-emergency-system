package algorithms;

import br.com.logisticsystem.algorithms.linear.RadixSort;
import br.com.logisticsystem.utils.Estatisticas;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RadixSortTest {

    @Test
    void deveOrdenarArrayDesordenado() {
        int[] array = {170, 45, 75, 90, 802, 24, 2, 66};

        Estatisticas estatisticas =
                new Estatisticas("RadixSort", array.length);

        RadixSort.ordenar(array, estatisticas);

        assertArrayEquals(
                new int[]{2, 24, 45, 66, 75, 90, 170, 802},
                array
        );
    }

    @Test
    void deveOrdenarArrayJaOrdenado() {
        int[] array = {1, 2, 3, 4, 5};

        Estatisticas estatisticas =
                new Estatisticas("RadixSort", array.length);

        RadixSort.ordenar(array, estatisticas);

        assertArrayEquals(
                new int[]{1, 2, 3, 4, 5},
                array
        );
    }

    @Test
    void deveOrdenarArrayInvertido() {
        int[] array = {5, 4, 3, 2, 1};

        Estatisticas estatisticas =
                new Estatisticas("RadixSort", array.length);

        RadixSort.ordenar(array, estatisticas);

        assertArrayEquals(
                new int[]{1, 2, 3, 4, 5},
                array
        );
    }

    @Test
    void deveOrdenarArrayComElementosRepetidos() {
        int[] array = {4, 2, 4, 1, 2, 3};

        Estatisticas estatisticas =
                new Estatisticas("RadixSort", array.length);

        RadixSort.ordenar(array, estatisticas);

        assertArrayEquals(
                new int[]{1, 2, 2, 3, 4, 4},
                array
        );
    }

    @Test
    void deveOrdenarArrayComZeros() {
        int[] array = {0, 50, 0, 20, 1};

        Estatisticas estatisticas =
                new Estatisticas("RadixSort", array.length);

        RadixSort.ordenar(array, estatisticas);

        assertArrayEquals(
                new int[]{0, 0, 1, 20, 50},
                array
        );
    }

    @Test
    void deveOrdenarArrayComUmElemento() {
        int[] array = {10};

        Estatisticas estatisticas =
                new Estatisticas("RadixSort", array.length);

        RadixSort.ordenar(array, estatisticas);

        assertArrayEquals(
                new int[]{10},
                array
        );
    }

    @Test
    void deveOrdenarArrayVazio() {
        int[] array = {};

        Estatisticas estatisticas =
                new Estatisticas("RadixSort", array.length);

        RadixSort.ordenar(array, estatisticas);

        assertArrayEquals(
                new int[]{},
                array
        );
    }

    @Test
    void naoDeveRegistrarComparacoes() {
        int[] array = {170, 45, 75, 90};

        Estatisticas estatisticas =
                new Estatisticas("RadixSort", array.length);

        RadixSort.ordenar(array, estatisticas);

        assertEquals(
                0,
                estatisticas.getComparacoes()
        );
    }

    @Test
    void deveRegistrarTrocas() {
        int[] array = {170, 45, 75, 90};

        Estatisticas estatisticas =
                new Estatisticas("RadixSort", array.length);

        RadixSort.ordenar(array, estatisticas);

        assertTrue(
                estatisticas.getTrocas() > 0
        );
    }

    @Test
    void deveManterQuantidadeDeElementos() {
        int[] array = {9, 5, 2, 8, 1, 4};

        int tamanhoAntes = array.length;

        Estatisticas estatisticas =
                new Estatisticas("RadixSort", tamanhoAntes);

        RadixSort.ordenar(array, estatisticas);

        assertEquals(
                tamanhoAntes,
                array.length
        );
    }

    @Test
    void deveOrdenarArrayGrande() {
        int[] array = new int[1000];

        for (int i = 0; i < array.length; i++) {
            array[i] = array.length - i;
        }

        Estatisticas estatisticas =
                new Estatisticas("RadixSort", array.length);

        RadixSort.ordenar(array, estatisticas);

        for (int i = 0; i < array.length - 1; i++) {
            assertTrue(array[i] <= array[i + 1]);
        }
    }
}